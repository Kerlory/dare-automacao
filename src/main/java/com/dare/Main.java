package com.dare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String URL_SEFIN = "https://dare.sefin.ro.gov.br/situacao-dare";

    public static StatusConsulta status = new StatusConsulta();

    public static List<String> consultarLista(List<String> codigos) {

        List<String> resultados = new ArrayList<>();

        status.total = codigos.size();
        status.processadas = 0;
        status.rodando = true;

        for (String codigo : codigos) {

            if (!status.rodando)
                break;

            if (codigo == null || codigo.trim().isEmpty())
                continue;

            try {

                String resposta = consultarCodigo(codigo.trim());

                String situacao = extrairSituacao(resposta);

                resultados.add(codigo + " - " + situacao);

            } catch (Exception e) {
                e.printStackTrace(); // 🔥 ajuda debug no Render
                resultados.add(codigo + " - ERRO");
            }

            status.processadas++;

            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }
        }

        status.rodando = false;

        return resultados;
    }

    private static String consultarCodigo(String codigo) throws Exception {

        var url = java.net.URI.create(URL_SEFIN).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        // 🔥 HEADERS (simula navegador real)
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept", "text/html");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        conn.setDoOutput(true);

        // 🔥 PARÂMETROS (IMPORTANTE)
        String params = "numero_guia_cbarras=" + codigo + "&botao=Consultar";

        OutputStream os = conn.getOutputStream();
        os.write(params.getBytes());
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();

        BufferedReader in;

        if (responseCode >= 200 && responseCode < 300) {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();
    }

    private static String extrairSituacao(String html) {

        html = html.toUpperCase();

        // ✅ CONSIDERAR COMO PAGO
        if (html.contains("PAGO") || html.contains("BAIXA PROVISÓRIA") || html.contains("BAIXA")) {
            return "PAGO";
        }

        // ❌ NÃO PAGO
        if (html.contains("NAO PAGO") || html.contains("NÃO PAGO")) {
            return "NÃO PAGO";
        }

        // ⚠️ NÃO ENCONTRADO
        return "NÃO ENCONTRADO";
    }
}