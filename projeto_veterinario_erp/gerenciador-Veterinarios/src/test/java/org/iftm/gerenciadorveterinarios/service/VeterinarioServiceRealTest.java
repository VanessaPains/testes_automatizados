package org.iftm.gerenciadorveterinarios.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.servicies.VeterinarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Teste de integração com as classes reais do sistema.
 *
 * creie este arquivo para demonstrar a execução sem Mockito,
 * usando o Spring Boot e o banco H2 em memória.
 */
@SpringBootTest
@ActiveProfiles("test")
class VeterinarioServiceRealTest {

    @Autowired
    private VeterinarioService service;

    //Parte 1 - Exercicio A5 - que usa exercicio A5 como base para o teste real, 
    //mas sem usar o Mockito, ou seja, usando a service real e o repositório real.
    @Test
    @Order(1)
    @DisplayName("deve buscar um veterinário usando a service real e o repositório real")
    public void deveBuscarVeterinarioComClassesReais() {
        // Arrange
        Integer idExistente = 1;

        // Act
        Optional<Veterinario> resultado = service.buscaVeterinariosPeloId(idExistente);

        // Assert
        assertTrue(resultado.isPresent(), "O veterinário deveria existir no banco de teste.");
        assertEquals("Conceicao ", resultado.get().getNome(),
                "A service real deve retornar o nome com a regra de truncamento aplicada.");
    }

    //este teste real valida a busca por parte do nome usando a service e o
    //repositório reais, sem mocks, para confirmar o comportamento do fluxo completo.
    @Test
    @Order(2)
    @DisplayName("deve encontrar veterinários por parte do nome com a service real")
    public void deveBuscarVeterinariosPorParteDoNomeComClassesReais() {
        // Arrange
        String parteNome = "Con";

        // Act
        List<Veterinario> resultado = service.buscaVeterinariosComParteNome(parteNome);

        // Assert
        assertFalse(resultado.isEmpty(), "Deveria encontrar ao menos um veterinário com essa parte do nome.");
        assertTrue(resultado.stream().anyMatch(v -> v.getNome().contains("Conceicao")),
                "O resultado real deve conter o veterinário esperado.");
    }

    //este teste verifica a listagem completa com o banco real em memória.
    @Test
    @Order(3)
    @DisplayName("deve listar todos os veterinários usando a service real")
    public void deveListarTodosVeterinariosComClassesReais() {
        // Act
        List<Veterinario> resultado = service.buscaTodosVeterinarios();

        // Assert
        assertEquals(6, resultado.size(), "O banco de teste real deve retornar todos os veterinários cadastrados.");
        assertFalse(resultado.isEmpty(), "A listagem real não deve ficar vazia.");
    }

    //este teste real cobre a remoção de um veterinário usando a service real.
    @Test
    @Order(4)
    @DisplayName("deve apagar um veterinário usando a service real")
    public void deveApagarVeterinarioComClassesReais() {
        // Arrange
        Veterinario veterinarioParaSalvar = new Veterinario(
                null,
                "Teste Real Delete",
                "teste-real@email.com",
                "Clínico",
                BigDecimal.valueOf(2000));

        int totalAntes = service.buscaTodosVeterinarios().size();

        // Act
        Veterinario veterinarioSalvo = service.salvar(veterinarioParaSalvar);
        service.apagar(veterinarioSalvo);
        int totalDepois = service.buscaTodosVeterinarios().size();

        // Assert
        assertNotNull(veterinarioSalvo.getId(), "O veterinário real deve ser salvo antes de apagar.");
        assertEquals(totalAntes, totalDepois,
                "A remoção real deve diminuir a quantidade de registros no banco de teste.");
    }

    //este teste confirma o comportamento de erro quando o ID não existe,
    //mantendo a mesma lógica do teste com Mockito, agora com fluxo real.
    @Test
    @Order(5)
    @DisplayName("deve lançar exceção ao apagar um ID inexistente com a service real")
    public void deveLancarExcecaoAoApagarIdInexistenteComClassesReais() {
        // Act + Assert
        assertThrows(RuntimeException.class, () -> service.apagarPorId(999999),
                "A service real deve lançar exceção quando o ID não existir.");
    }
}
