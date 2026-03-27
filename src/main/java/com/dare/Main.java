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
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String params = "numero_guia_cbarras=" + codigo;

        OutputStream os = conn.getOutputStream();
        os.write(params.getBytes());
        os.flush();
        os.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

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

        // ⚠️ OUTROS CASOS
        return "NÃO ENCONTRADO";
    }
}