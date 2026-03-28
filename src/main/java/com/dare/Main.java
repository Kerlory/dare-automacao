package com.dare;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    private static final String URL = "https://dare.sefin.ro.gov.br/situacao-dare";

    public static StatusConsulta status = new StatusConsulta();

    public static List<String> consultarLista(List<String> codigos) {

        List<String> resultados = Collections.synchronizedList(new ArrayList<>());

        status.total = codigos.size();
        status.processadas = 0;
        status.rodando = true;

        try {

            WebDriverManager.chromedriver().setup();

            ExecutorService pool = Executors.newFixedThreadPool(3);

            List<Future<String>> tarefas = new ArrayList<>();

            for (String codigo : codigos) {

                tarefas.add(pool.submit(() -> consultarGuia(codigo)));

            }

            for (Future<String> f : tarefas) {

                try {
                    resultados.add(f.get());
                } catch (Exception e) {
                    resultados.add("ERRO - FALHA NA CONSULTA");
                }

                status.processadas++;
            }

            pool.shutdown();

        } catch (Exception e) {

            e.printStackTrace();
            resultados.add("ERRO GERAL NO SERVIDOR");

        }

        status.rodando = false;

        return resultados;
    }

    private static String consultarGuia(String codigo) {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            driver.get(URL);

            WebElement campo = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.name("numero_guia_cbarras")));

            campo.clear();
            campo.sendKeys(codigo);

            WebElement botao = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("btn-consulta-pgto")));

            botao.click();

            WebElement resultado = wait.until(d -> {

                List<WebElement> els = d.findElements(By.tagName("h5"));

                for (WebElement el : els) {

                    String t = el.getText();

                    if (t != null && !t.isEmpty()) {

                        if (t.contains("PAGO") ||
                                t.contains("BAIXA PROVISÓRIA") ||
                                t.contains("NAO") ||
                                t.contains("NÃO")) {

                            return el;
                        }

                    }
                }

                return null;

            });

            String statusTexto = resultado.getText().trim();

            return codigo + " - " + statusTexto;

        } catch (Exception e) {

            return codigo + " - NÃO ENCONTRADO";

        } finally {

            driver.quit();

        }
    }
}