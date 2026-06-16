package org.iftm.selenium_webdriver;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class VirtualTest {
    private WebDriver driver;

    @BeforeEach
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    /**
     * Cenário de teste
     * Testar o site: https://virtualif.iftm.edu.br/VRTL/
     * Verificar se cep correto retorna endereço correto.
     * @throws InterruptedException 
     */
    @Test
    public void testarAutenticarSemInformarDadosRetornaMensagem() throws InterruptedException{
        //arrange
        String urlTestada = "https://virtualif.iftm.edu.br/VRTL/";
        String tituloPaginaTestada = "VirtualIF - IFTM";
        String respostaEsperada = "O usuário deve ser informado!";

        //definir a página a ser testada
        driver.get(urlTestada);
        assertEquals(tituloPaginaTestada, driver.getTitle());

        WebElement botaoEntrar = driver.findElement(By.id("btnEntrar"));
        //botaoEntrar.click(); não funciona
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botaoEntrar);
        Thread.sleep(1000); //irá aguardar a atualização da página
        WebElement mensagemErro = driver.findElement(By.id("status-login"));

        assertEquals(respostaEsperada, mensagemErro.getText());
        
    }

    
    @AfterEach
    public void exit(){
        driver.close();
    }
    
}
