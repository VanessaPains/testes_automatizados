package org.iftm.gerenciadorveterinarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.iftm.gerenciadorveterinarios.servicies.VeterinarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VeterinarioServiceTeste_VanessaPains {

    @Mock
    private VeterinarioRepository repositorio;

    @InjectMocks
    private VeterinarioService service;

    // Parte Exercicio A5 - que usa exercicio A5 como base para o teste real,
    // mas sem usar o Mockito, ou seja, usando a service real e o repositório real.
    @Test
    public void testarBuscarVeterinarioPorIDExistenteRetornaVeterinarioComTruncado() {
        // arrange
        String nomeExistente = "Erica Queiroz Pinto";
        int tamanhoEsperadoNome = 10;
        String nomeEsperado = "Erica Quei";

        Veterinario veterinarioEsperado = new Veterinario(null, nomeExistente,
                "", "", BigDecimal.valueOf(0));

        when(repositorio.findById(Mockito.anyInt())).thenReturn(Optional.of(veterinarioEsperado));

        Optional<Veterinario> resposta = service.buscaVeterinariosPeloId(Mockito.anyInt());
        Veterinario veterinarioRetornado = resposta.get();

        assertTrue(resposta.isPresent());
        assertEquals(tamanhoEsperadoNome, veterinarioRetornado.getNome().length());
        assertEquals(nomeEsperado, veterinarioRetornado.getNome());

        verify(repositorio).findById(Mockito.anyInt());
    }

    // este teste confirma o comportamento de erro quando o ID não existe, mantendo a mesma lógica do teste com Mockito, agora com fluxo real.
    @Test
    public void testarApagarRealmenteApagaRegistro() {
        Integer idExistente = 2;

        String nomeExistente = "Erica Queiroz Pinto";
        Veterinario veterinarioExcluido = new Veterinario(
                idExistente,
                nomeExistente,
                "",
                "",
                null);

        assertDoesNotThrow(() -> {
            service.apagar(veterinarioExcluido);
        });

        verify(repositorio).delete(veterinarioExcluido);
    }

    // este teste verifica a busca por parte do nome usando a service e o repositório reais, sem mocks, para confirmar o comportamento do fluxo completo.
    @Test
    @Order(1)
    public void deveApagarVeterinario_quandoVeterinarioExistir() {

        Integer idExistente = 2;

        Veterinario veterinario = new Veterinario(
                idExistente,
                "Erica Queiroz Pinto",
                "",
                "",
                null);

        assertDoesNotThrow(() -> {
            service.apagar(veterinario);
        });

        verify(repositorio).delete(veterinario);
    }

    //
    @Test
    @Order(2)
    public void deveRetornarVeterinario_quandoBuscarPorIdExistente() {

        Integer idExistente = 1;

        Veterinario veterinarioEsperado = new Veterinario(
                idExistente,
                "Erica Queiroz Pinto",
                "",
                "",
                BigDecimal.ZERO);

        when(repositorio.findById(idExistente))
                .thenReturn(Optional.of(veterinarioEsperado));

        Optional<Veterinario> resposta = service.buscaVeterinariosPeloId(idExistente);

        assertTrue(resposta.isPresent());

        assertEquals(
                "Erica Quei",
                resposta.get().getNome());

        verify(repositorio).findById(idExistente);
    }

    // este teste verifica a busca por parte do nome usando a service e o repositório reais, sem mocks, para confirmar o comportamento do fluxo completo.
    @Test
    @Order(4)
    public void deveRetornarListaComDoisVeterinarios_quandoBuscarParteDoNome() {

        String parteNome = "Silva";

        Veterinario veterinario1 = new Veterinario(
                1,
                "Ana Silva",
                "",
                "",
                BigDecimal.valueOf(3000));

        Veterinario veterinario2 = new Veterinario(
                2,
                "Carlos Silva",
                "",
                "",
                BigDecimal.valueOf(4000));

        List<Veterinario> listaEsperada = List.of(veterinario1, veterinario2);

        when(repositorio.findByNomeContains(parteNome)).thenReturn(listaEsperada);

        List<Veterinario> resultado = service.buscaVeterinariosComParteNome(parteNome);

        assertEquals(2, resultado.size());

        verify(repositorio).findByNomeContains(parteNome);
    }

    // este teste verifica a listagem completa com o banco real em memória.
    @Test
    @Order(5)
    @SuppressWarnings("null")
    public void deveLancarExcecaoAoApagar_quandoIdNaoExistir() {

        Integer idInexistente = 999;

        when(repositorio.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.apagarPorId(idInexistente);
        });

        verify(repositorio, never()).delete((Veterinario) any());
    }

    // mas sem usar o Mockito, ou seja, usando a service real e o repositório real.
    @Test
    @Order(6)
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

    // este teste real valida a busca por parte do nome usando a service e o
    // repositório reais, sem mocks, para confirmar o comportamento do fluxo
    // completo.
    @Test
    @Order(7)
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

    // este teste verifica a listagem completa com o banco real em memória.
    @Test
    @Order(8)
    @DisplayName("deve listar todos os veterinários usando a service real")
    public void deveListarTodosVeterinariosComClassesReais() {
        // Act
        List<Veterinario> resultado = service.buscaTodosVeterinarios();

        // Assert
        assertEquals(6, resultado.size(), "O banco de teste real deve retornar todos os veterinários cadastrados.");
        assertFalse(resultado.isEmpty(), "A listagem real não deve ficar vazia.");
    }

    // este teste real cobre a remoção de um veterinário usando a service real.
    @Test
    @Order(9)
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

    // este teste confirma o comportamento de erro quando o ID não existe,
    // mantendo a mesma lógica do teste com Mockito, agora com fluxo real.
    @Test
    @Order(10)
    @DisplayName("deve lançar exceção ao apagar um ID inexistente com a service real")
    public void deveLancarExcecaoAoApagarIdInexistenteComClassesReais() {
        // Act + Assert
        assertThrows(RuntimeException.class, () -> service.apagarPorId(999999),
                "A service real deve lançar exceção quando o ID não existir.");
    }
}
