package org.iftm.selenium_webdriver;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GerenciadorVeterinarioTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        baseUrl = "http://localhost:" + port + "/home";
    }

    @Test
    public void testarTelaInicialCarregaDadosExistentesNoBD(){
        String nomePrimeiraLinha = "Conceição Evaristo";
        String nomeSegundaLinha = "Erica Queiroz Pinto";
        String tituloPagina = "Gerenciador de Veterinários";

        abrirPaginaInicial();
        assertEquals(tituloPagina, driver.getTitle());
        assertEquals(nomePrimeiraLinha, obterTextoCelula(1, 2));
        assertEquals(nomeSegundaLinha, obterTextoCelula(2, 2));
    }

    @Test
    public void testarListarVeterinariosExibeTabela(){
        abrirPaginaInicial();

        WebElement tabela = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        assertTrue(tabela.isDisplayed());
        assertEquals("Gerenciador de Veterinários", driver.getTitle());
        assertFalse(driver.findElements(By.xpath("//tbody/tr")).isEmpty());
    }

    @Test
    public void testarCadastrarVeterinarioComSucesso(){
        String nome = "Ana Teste Selenium";
        String email = "ana.selenium@test.local";
        String especialidade = "pequenos";
        String salario = "4500";

        abrirPaginaInicial();

        enviarTextoCampo("nome", nome);
        enviarTextoCampo("email", email);
        enviarTextoCampo("especialidade", especialidade);
        enviarTextoCampo("salario", salario);

        clicarBotaoPorTexto("cadastrar");
        WebElement novaLinha = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[td[2][normalize-space(.)='" + nome + "']]")));

        assertEquals(nome, novaLinha.findElement(By.xpath("./td[2]")).getText());
        assertEquals(email, novaLinha.findElement(By.xpath("./td[3]")).getText());
    }

    @Test
    public void testarPesquisarVeterinarioPorNome(){
        String nomePesquisa = "Erica Queiroz Pinto";

        abrirPaginaInicial();

        enviarTextoCampo("pesquisa", nomePesquisa);
        clicarBotaoPorTexto("pesquisar");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr[1]/td[2]")));

        assertEquals(nomePesquisa, obterTextoCelula(1, 2));
    }

    @Test
    public void testarAlterarVeterinarioEVerificarDadosAtualizados(){
        abrirPaginaInicial();

        String nomeOriginal = obterTextoCelula(1, 2);
        String novoEmail = "alterado." + System.currentTimeMillis() + "@test.local";

        clicarBotaoPorAcoesNaLinha(nomeOriginal, "alterar");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='email']")));
        enviarTextoCampo("email", novoEmail);
        clicarBotaoPorTexto("salvar");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//tbody/tr[td[2][normalize-space(.)='" + nomeOriginal + "']]/td[3]"), novoEmail));
        WebElement linha = driver.findElement(By.xpath("//tbody/tr[td[2][normalize-space(.)='" + nomeOriginal + "']]") );
        assertEquals(novoEmail, linha.findElement(By.xpath("./td[3]")).getText());
    }

    @Test
    public void testarExcluirVeterinarioRemoveLinhaDaTabela(){
        abrirPaginaInicial();

        int quantidadeAntes = driver.findElements(By.xpath("//tbody/tr")).size();
        String nomeExcluir = obterTextoCelula(1, 2);

        clicarBotaoPorAcoesNaLinha(nomeExcluir, "excluir");
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.xpath("//tbody/tr"), quantidadeAntes));

        List<WebElement> linhas = driver.findElements(By.xpath("//tbody/tr[td[2][contains(.,'" + nomeExcluir + "')]]"));
        assertTrue(linhas.isEmpty());
    }

    private void abrirPaginaInicial() {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.titleContains("Gerenciador de Veterinários"));
    }

    private String obterTextoCelula(int linha, int coluna) {
        WebElement celula = driver.findElement(By.xpath("//tbody/tr[" + linha + "]/td[" + coluna + "]"));
        return celula.getText();
    }

    private void enviarTextoCampo(String chave, String valor) {
        By localizador = By.xpath("//input[(contains(translate(@id,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + chave + "') or contains(translate(@name,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + chave + "'))]");
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(localizador));
        campo.clear();
        campo.sendKeys(valor);
    }

    private void clicarBotaoPorTexto(String texto) {
        By localizador = By.xpath("//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + texto.toLowerCase() + "')]");
        WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(localizador));
        botao.click();
    }

    private void clicarBotaoPorAcoesNaLinha(String nomeLinha, String acao) {
        By localizador = By.xpath("//tbody/tr[td[2][contains(.,'" + nomeLinha + "')]]//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + acao.toLowerCase() + "')]");
        WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(localizador));
        botao.click();
    }

    @AfterEach
    public void exit(){
        if (driver != null) {
            driver.quit();
        }
    }
}
