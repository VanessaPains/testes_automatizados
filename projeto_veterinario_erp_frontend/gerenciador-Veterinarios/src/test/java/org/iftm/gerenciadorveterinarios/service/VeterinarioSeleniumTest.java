// Declara o pacote desta classe de teste
package org.iftm.gerenciadorveterinarios.service;

// Import estático para assertions do JUnit
import static org.junit.jupiter.api.Assertions.*;

// Imports utilitários do Java
import java.time.Duration; // para timeouts
import java.util.List; // para manipular listas de elementos
import java.util.UUID; // para gerar nomes únicos

// Anotações e recursos do JUnit
import org.junit.jupiter.api.AfterAll; // executa após todos os testes
import org.junit.jupiter.api.BeforeAll; // executa antes de todos os testes
import org.junit.jupiter.api.BeforeEach; // executa antes de cada teste
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test; // marca um método como teste
// Selenium: seleção de elementos e controle do navegador
import org.openqa.selenium.By; // localizadores de elementos
import org.openqa.selenium.WebDriver; // interface do driver
import org.openqa.selenium.WebElement; // representa elementos da página
import org.openqa.selenium.chrome.ChromeDriver; // driver do Chrome
import org.openqa.selenium.chrome.ChromeOptions; // opções do Chrome
import org.openqa.selenium.support.ui.ExpectedConditions; // condições esperadas
import org.openqa.selenium.support.ui.WebDriverWait; // espera explícita
// Spring Boot test support para inicializar a aplicação durante os testes
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort; // porta randômica

// WebDriverManager para gerenciar binários do driver automaticamente
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Classe de testes E2E com Selenium para funcionalidades de Veterinário
public class VeterinarioSeleniumTest {

    // Driver compartilhado entre todos os testes
    private static WebDriver driver;

    // Espera explícita para sincronizar ações com a página
    private WebDriverWait wait;


    //FAZER O TESTE RODAR MAIS LENTO PARA VER AS AÇÕES NO NAVEGADOR (OPCIONAL)
    // Delay configurável entre ações (em milissegundos). Use -Dselenium.actionDelay=500
    private static final long ACTION_DELAY;
    static {
        long v = 0L;
        try {
            v = Long.parseLong(System.getProperty("selenium.actionDelay", "20"));
            if (v < 0) v = 0L;
        } catch (NumberFormatException ex) {
            v = 0L;
        }
        ACTION_DELAY = v;
    }
    // Duração total desejada para preencher cada campo (em ms). Use -Dselenium.fieldFillDuration=5000
    private static final long FIELD_FILL_DURATION;
    static {
        long f = 0L;
        try {
            f = Long.parseLong(System.getProperty("selenium.fieldFillDuration", "400"));
            if (f < 0) f = 0L;
        } catch (NumberFormatException ex) {
            f = 0L;
        }
        FIELD_FILL_DURATION = f;
    }
    //FIM


    
    // Porta aleatória onde o Spring Boot sobe a aplicação para testes
    @LocalServerPort
    private int port;//minha porta é

    // Configura o WebDriver antes de qualquer teste ser executado
    @BeforeAll
    static void setupWebDriver() {
        // Baixa/resolve o binário do ChromeDriver automaticamente
        WebDriverManager.chromedriver().setup();

        // Configura opções do Chrome
        ChromeOptions options = new ChromeOptions();
        // Lê flag de sistema para decidir headless (padrão false)
        boolean headless = Boolean.parseBoolean(System.getProperty("selenium.headless", "false"));
        // Se headless estiver ativado, adiciona argumentos para modo headless
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
        }
        // Define tamanho da janela para evitar problemas de responsividade
        options.addArguments("--window-size=1920,1080");
        // Argumentos úteis em ambientes Linux/CI
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        // Instancia o ChromeDriver com as opções configuradas
        driver = new ChromeDriver(options);
    }

    // Encerra o WebDriver após todos os testes
    @AfterAll
    static void tearDownWebDriver() {
        // Se o driver estiver inicializado, encerra o processo
        if (driver != null) {
            driver.quit();
        }
    }

    // Executado antes de cada teste para criar a espera explícita
    @BeforeEach
    private void setupWait() {
        // Timeout generoso para evitar falhas por carregamentos lentos
        wait = new WebDriverWait(driver, Duration.ofSeconds(120));
    }

    // Retorna a URL base da aplicação em teste
    private String baseUrl() {
        sleepIfNeeded();
        return "http://localhost:" + port;
        
    }

    // Abre a página inicial (/home) e espera o título principal existir
    private void openHomePage() {
        driver.get(baseUrl() + "/home");//navega para a URL base + /home
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        // Pausa opcional para observar a página inicial
        sleepIfNeeded();
    }

    // Procura na tabela a linha que contenha o nome informado e retorna o elemento da linha
    private WebElement findVeterinarioRowByName(String nome) {
        // Seleciona todas as linhas da tabela de veterinários
        List<WebElement> rows = driver.findElements(By.cssSelector("table.table-light tr"));
        for (WebElement row : rows) {
            // Lê o texto da linha
            String text = row.getText();
            // Se o texto da linha conter o nome procurado, retorna essa linha
            if (text.contains(nome)) {
                return row;
            }
        }
        // Retorna null se não encontrou
        return null;
    }

    // Preenche o formulário de veterinário com os valores passados
    private void fillVeterinarioForm(String nome, String email, String especialidade, String salario) {
        // Aguarda o campo nome ficar visível e obtém os inputs pelos ids
        WebElement nomeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
        WebElement emailInput = driver.findElement(By.id("inputEmail"));
        WebElement especialidadeInput = driver.findElement(By.id("inputEspecialidade"));
        WebElement salarioInput = driver.findElement(By.id("inputSalario"));

        // Limpa e insere os valores nos campos (com preenchimento lento se configurado)
        sendKeysWithTotalDuration(nomeInput, nome, FIELD_FILL_DURATION);
        sendKeysWithTotalDuration(emailInput, email, FIELD_FILL_DURATION);
        sendKeysWithTotalDuration(especialidadeInput, especialidade, FIELD_FILL_DURATION);
        sendKeysWithTotalDuration(salarioInput, salario, FIELD_FILL_DURATION);

        // Verificações imediatas que o valor foi inserido corretamente (assert value)
        assertEquals(nome, nomeInput.getAttribute("value"), "Deve preencher o campo Nome com o valor correto");
        assertEquals(email, emailInput.getAttribute("value"), "Deve preencher o campo Email com o valor correto");
        assertEquals(especialidade, especialidadeInput.getAttribute("value"), "Deve preencher o campo Especialidade com o valor correto");
        assertEquals(salario, salarioInput.getAttribute("value"), "Deve preencher o campo Salario com o valor correto");
        // Pausa opcional após preencher o formulário
        sleepIfNeeded();
    }

    // Envia o texto caractere a caractere calculando um delay para atingir totalMillis.
    // Se totalMillis <= 0 envia normalmente em uma única chamada.
    private void sendKeysWithTotalDuration(WebElement input, String text, long totalMillis) {
        input.clear();
        if (text == null || text.isEmpty()) {
            return;
        }
        if (totalMillis <= 0) {
            input.sendKeys(text);
            return;
        }
        long perChar = Math.max(1, totalMillis / text.length());
        for (char c : text.toCharArray()) {
            input.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(perChar);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Submete o formulário e espera redirecionamento de volta para /home
    private void submitForm() {
        // Encontra o botão de submit e clica
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        // Aguarda a URL conter /home e o título estar visível
        wait.until(ExpectedConditions.urlContains("/home"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        // Pausa opcional após submissão
        sleepIfNeeded();
    }

    // Gera um nome curto e único para uso nos testes (evita truncamento)
    private String createUniqueNome() {
        // Gera um sufixo único e concatena com prefixo curto
        return "Vet" + UUID.randomUUID().toString().substring(0, 6);
    }

        // Pausa controlada por ACTION_DELAY para tornar ações visíveis
    private void sleepIfNeeded() {
        if (ACTION_DELAY > 0) {
            try {
                Thread.sleep(ACTION_DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    //===== TESTES SELENIUM =====
    // Teste de cadastro de veterinário
    @Test
    @Order(1)
    void deveCadastrarVeterinario() {
        // Abre a página inicial
        openHomePage();

        // Clica no link que leva ao formulário de cadastro (/form)
        driver.findElement(By.cssSelector("a[href='/form']")).click();

        sleepIfNeeded();// Pausa opcional para observar a navegação para o formulário

        // Aguarda o campo nome aparecer
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));

        // Preenche dados de teste
        String nome = "Vanessa Pains - " +createUniqueNome();
        String email = "selenium.cadastrar@example.com";
        String especialidade = "Equinos e Bovinos";
        String salario = "7500.00";

        // Preenche o formulário e submete
        fillVeterinarioForm(nome, email, especialidade, salario);
        submitForm();

        // Verifica se o registro cadastrado aparece na lista (assert text)
        WebElement createdRow = findVeterinarioRowByName(nome);
        assertNotNull(createdRow, "O veterinário cadastrado deve aparecer na lista");
        assertTrue(createdRow.getText().contains(nome), "A linha deve conter o nome do veterinário");
        assertTrue(createdRow.getText().contains(email), "A linha deve conter o email do veterinário");
    }

    // Teste de pesquisa por nome
    @Test
    @Order(2)
    void devePesquisarVeterinario() {
        // Gera dados e cadastra um registro para pesquisar
        String nome = "Vanessa Pains - " + createUniqueNome();
        String email = "selenium.pesquisa@example.com";
        String especialidade = "Grandes";
        String salario = "4200.00";

        //abrir a página inicial, navegar para o formulário, preencher e submeter para criar o registro a ser pesquisado
        openHomePage();

        // Clica no link que leva ao formulário de cadastro (/form)
        driver.findElement(By.cssSelector("a[href='/form']")).click();

        sleepIfNeeded();// Aguarda o campo nome aparecer

        //fill serve para preencher o formulário de cadastro com os dados gerados e submeter
        fillVeterinarioForm(nome, email, especialidade, salario);
        submitForm();

        // Abre o formulário de pesquisa (/find) e insere o nome
        driver.findElement(By.cssSelector("a[href='/find']")).click();
        sleepIfNeeded();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));

        WebElement nomePesquisaInput = driver.findElement(By.id("nome"));
        nomePesquisaInput.clear();
        nomePesquisaInput.sendKeys(nome);
        // Verifica que o campo de pesquisa foi populado corretamente
        assertEquals(nome, nomePesquisaInput.getAttribute("value"), "O campo de pesquisa deve conter o nome informado");

        // Submete o formulário de pesquisa e valida resultado na tabela
        submitForm();

        // Verifica se a tabela exibe o veterinário pesquisado (assert text)
        WebElement row = findVeterinarioRowByName(nome);
        assertNotNull(row, "A pesquisa deve retornar o veterinário criado");
        assertTrue(row.getText().contains(especialidade), "A tabela deve mostrar a especialidade do veterinário pesquisado");
    }

    // Teste de edição de registro
    @Test
    @Order(3)
    void deveAlterarVeterinario() {
        // Cria e cadastra um veterinário
        String nome = createUniqueNome();
        String email = "selenium.alterar@example.com";
        String especialidade = "Clínico";
        String salario = "3900.00";

        openHomePage();
        driver.findElement(By.cssSelector("a[href='/form']")).click();

        sleepIfNeeded();// Aguarda o campo nome aparecer

        fillVeterinarioForm(nome, email, especialidade, salario);
        submitForm();

        // Localiza a linha do veterinário cadastrado
        WebElement row = findVeterinarioRowByName(nome);
        assertNotNull(row, "O veterinário deve existir antes da edição");

        // Clica no botão de editar (classe btn-warning) na linha encontrada
        WebElement editButton = row.findElement(By.cssSelector("a.btn-warning"));
        editButton.click();
        sleepIfNeeded();

        // Aguarda carregamento do formulário de edição e verifica valores carregados
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
        assertEquals(nome, driver.findElement(By.id("nome")).getAttribute("value"), "O nome deve ser carregado no formulário de edição");
        assertEquals(email, driver.findElement(By.id("inputEmail")).getAttribute("value"), "O email deve ser carregado no formulário de edição");
        assertEquals(especialidade, driver.findElement(By.id("inputEspecialidade")).getAttribute("value"), "A especialidade deve ser carregada no formulário de edição");
        assertEquals(salario, driver.findElement(By.id("inputSalario")).getAttribute("value"), "O salário deve ser carregado no formulário de edição");

        // Atualiza email e salário e submete
        String novoEmail = "selenium.atualizado@example.com";
        String novoSalario = "9800.00";

        fillVeterinarioForm(nome, novoEmail, especialidade, novoSalario);
        submitForm();

        // Valida se as alterações aparecem na tabela
        WebElement updatedRow = findVeterinarioRowByName(nome);
        assertNotNull(updatedRow, "O veterinário editado deve aparecer na lista");
        assertTrue(updatedRow.getText().contains(novoEmail), "A tabela deve exibir o email atualizado");
        assertTrue(updatedRow.getText().contains("4500"), "A tabela deve exibir o salário atualizado");
    }

    // Teste de exclusão de registro
    @Test
    @Order(4)   
    void deveExcluirVeterinario() {
        // Cadastra um veterinário para remoção
        String nome = createUniqueNome();
        String email = "selenium.excluir@example.com";
        String especialidade = "Cirurgião";
        String salario = "4600.00";

        openHomePage();
        driver.findElement(By.cssSelector("a[href='/form']")).click();

        sleepIfNeeded();// Aguarda o campo nome aparecer ficar mais lento

        fillVeterinarioForm(nome, email, especialidade, salario);
        submitForm();

        // Localiza a linha e clica no botão de excluir (classe btn-danger)
        WebElement row = findVeterinarioRowByName(nome);
        assertNotNull(row, "O veterinário deve existir antes da exclusão");

        WebElement deleteButton = row.findElement(By.cssSelector("a.btn-danger"));
        deleteButton.click();
        sleepIfNeeded();

        // Aguarda voltar para /home e confirma que o registro não aparece mais
        wait.until(ExpectedConditions.urlContains("/home"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));

        WebElement deletedRow = findVeterinarioRowByName(nome);
        assertFalse(deletedRow != null && deletedRow.getText().contains(nome), "O veterinário deve ser removido da lista após exclusão");
    }

    // Teste que valida listagem e colunas da tabela
    @Test
    @Order(5)
    void deveListarVeterinario() {
        // Abre a página inicial
        openHomePage();

        // Verifica título da página
        WebElement heading = driver.findElement(By.tagName("h1"));
        assertEquals("Veterinarios", heading.getText(), "A página de lista deve exibir o título correto");

        // Verifica que há ao menos uma linha (além do cabeçalho)
        List<WebElement> rows = driver.findElements(By.cssSelector("table.table-light tr"));
        assertTrue(rows.size() > 1, "A tabela deve conter pelo menos um veterinário listado");

        // Verifica o nome da coluna Nome no cabeçalho
        WebElement headerNome = driver.findElement(By.cssSelector("table.table-light tr.table-dark th:nth-child(2)"));
        assertEquals("Nome", headerNome.getText(), "A tabela deve exibir a coluna Nome");
    }
}
