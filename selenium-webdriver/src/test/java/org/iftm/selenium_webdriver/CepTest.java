package org.iftm.selenium_webdriver;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CepTest {
    private WebDriver driver;

    @BeforeEach
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    /**
     * Cenário de teste
     * Testar o site: https://www.achecep.com.br/
     * Verificar se cep correto retorna endereço correto.
     */
    @Test
    public void testarBuscaCepRetornaEnderecoCorreto(){
        //arrange
        String urlTestada = "https://www.achecep.com.br/";
        String cepBuscado = "38401-036";
        String tituloPaginaTestada = "Busca CEP Correios - Consulta e Pesquisa CEP das Ruas";
        String tituloPesquisa = "Busca CEP 38401-036 em Minas Gerais - MG";


        //definir a página a ser testada
        driver.get(urlTestada);
        WebElement inputCep = driver.findElement(By.name("q"));
        WebElement botaoConsultar = driver.findElement(By.cssSelector("#btnConsultar"));
        assertEquals(urlTestada, driver.getCurrentUrl());
        assertEquals(tituloPaginaTestada, driver.getTitle());


        inputCep.sendKeys(cepBuscado);
        botaoConsultar.click();

        WebElement linkCep = driver.findElement(By.linkText("38401-036"));
        WebElement linkRua = driver.findElement(By.linkText("Rua Osmar Sales Monteiro"));

        //assert
        assertEquals(tituloPesquisa, driver.getTitle());
        assertEquals(cepBuscado, linkCep.getText());
        assertEquals("Rua Osmar Sales Monteiro", linkRua.getText());
    }

    
    @AfterEach
    public void exit(){
        driver.close();
    }
    

}
