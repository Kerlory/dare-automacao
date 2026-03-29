package com.dare;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class Main {

    private static final String URL = "https://dare.sefin.ro.gov.br/situacao-dare";

    public static StatusConsulta status = new StatusConsulta();

    public static List<String> consultarLista(List<String> codigos) {

        status.total = codigos.size();
        status.processadas = 0;
        status.rodando = true;

        List<String> resultados = new ArrayList<>();

        try {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            WebDriver driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            for (String codigo : codigos) {

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

                    resultados.add(codigo + " - " + resultado.getText());

                } catch (Exception e) {
                    resultados.add(codigo + " - ERRO");
                }

                status.processadas++;

                Thread.sleep(200); // 🔥 mais rápido que antes

            }

            driver.quit();

        } catch (Exception e) {
            resultados.add("ERRO GERAL");
        }

        status.rodando = false;

        return resultados;
    }
}