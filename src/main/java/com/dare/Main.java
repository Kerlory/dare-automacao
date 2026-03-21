package com.dare;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String URL = "https://dare.sefin.ro.gov.br/situacao-dare";

    public static List<String> consultarLista(List<String> codigos) {

        List<String> resultados = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String codigo : codigos) {

            if (codigo == null || codigo.trim().isEmpty())
                continue;

            try {

                // 🔥 SEMPRE VOLTA PRO INÍCIO (MELHOR QUE CLICAR VOLTAR)
                driver.get(URL);

                WebElement campo = wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                By.name("numero_guia_cbarras")));

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].focus();",
                        campo);

                campo.sendKeys(Keys.CONTROL + "a");
                campo.sendKeys(Keys.DELETE);

                Thread.sleep(100);

                // 🔥 cola direto
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1];",
                        campo,
                        codigo.trim());

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].dispatchEvent(new Event('input'));",
                        campo);

                System.out.println("✔ Colado: " + codigo);

                Thread.sleep(100);

                // 🔥 botão correto
                WebElement botao = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.id("btn-consulta-pgto")));

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();",
                        botao);

                // 🔥 resultado
                WebElement resultado = wait.until(d -> {
                    List<WebElement> els = d.findElements(By.tagName("h5"));

                    for (WebElement el : els) {
                        String t = el.getText();

                        if (t == null || t.trim().isEmpty())
                            continue;

                        if (t.contains("PAGO") || t.contains("NAO") || t.contains("NÃO")) {
                            return el;
                        }
                    }
                    return null;
                });

                String status = resultado.getText().trim();

                resultados.add(codigo + " - " + status);

                System.out.println("✔ Resultado: " + status);

            } catch (Exception e) {
                resultados.add(codigo + " - ERRO");
                System.out.println("❌ Erro com: " + codigo);
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }

        driver.quit();

        return resultados;
    }
}