package com.dare;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String URL = "https://dare.sefin.ro.gov.br/situacao-dare";

    public static StatusConsulta status = new StatusConsulta();

    public static List<String> consultarLista(List<String> codigos) {

        List<String> resultados = new ArrayList<>();

        status.total = codigos.size();
        status.processadas = 0;
        status.rodando = true;

        WebDriver driver = null;

        try {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            driver = new ChromeDriver(options);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            for (String codigo : codigos) {

                if (codigo == null || codigo.trim().isEmpty())
                    continue;

                try {

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

                    resultados.add(codigo + " - " + statusTexto);

                } catch (Exception e) {

                    resultados.add(codigo + " - NÃO ENCONTRADO");

                }

                status.processadas++;

                Thread.sleep(300);

            }

        } catch (Exception e) {

            e.printStackTrace();
            resultados.add("ERRO GERAL NO SERVIDOR");

        } finally {

            if (driver != null) {
                driver.quit();
            }

        }

        status.rodando = false;

        return resultados;
    }
}