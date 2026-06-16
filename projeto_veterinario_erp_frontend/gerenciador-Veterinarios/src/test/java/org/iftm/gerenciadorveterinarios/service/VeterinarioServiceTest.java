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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VeterinarioServiceTest {

    // cria a simulação/mock de todas as classes que serão injetadas na classe a ser
    // testada
    @Mock
    private VeterinarioRepository repositorio;

    // classe a ser testada que receberá todas as injeções de classes mock
    @InjectMocks
    private VeterinarioService service;

    /*
     * Validar se a busca por veterinario retorna o veterinário correto e o nome com
     * no máximo 10 caracteres.
     *
     * Este teste foi ajustado para cobrir a regra que foi corrigida no serviço.
     */
    @Test
    public void testarBuscarVeterinarioPorIDExistenteRetornaVeterinarioComTruncado() {
        // arrange
        String nomeExistente = "Erica Queiroz Pinto";// esse tem 19 é ára induzir ao erro.
        int tamanhoEsperadoNome = 10;
        String nomeEsperado = "Erica Quei";// esse exemplo de como ele tivesse so 10 caracteres

        Veterinario veterinarioEsperado = new Veterinario(null, nomeExistente,
                "", "", BigDecimal.valueOf(0));

        // configurar o mock - criando o cenário de teste
        when(repositorio.findById(Mockito.anyInt())).thenReturn(Optional.of(veterinarioEsperado));

        // act
        Optional<Veterinario> resposta = service.buscaVeterinariosPeloId(Mockito.anyInt());
        Veterinario veterinarioRetornado = resposta.get();

        // assert
        assertTrue(resposta.isPresent());
        assertEquals(tamanhoEsperadoNome, veterinarioRetornado.getNome().length());
        assertEquals(nomeEsperado, veterinarioRetornado.getNome());

        verify(repositorio).findById(Mockito.anyInt());
    }

    @Test
    public void testarApagarRealmenteApagaRegistro() {
        // Arrange
        Integer idExistente = 2;

        String nomeExistente = "Erica Queiroz Pinto";
        Veterinario veterinarioExcluido = new Veterinario(
                idExistente,
                nomeExistente,
                "",
                "",
                null);

        // act and assert
        assertDoesNotThrow(() -> {
            service.apagar(veterinarioExcluido);
        });

        verify(repositorio).delete(veterinarioExcluido);
    }

    @Test
    @Order(1)
    public void deveApagarVeterinario_quandoVeterinarioExistir() {

        // ARRANGE
        Integer idExistente = 2;

        Veterinario veterinario = new Veterinario(
                idExistente,
                "Erica Queiroz Pinto",
                "",
                "",
                null);

        // ACT + ASSERT
        assertDoesNotThrow(() -> {
            service.apagar(veterinario);
        });

        // VERIFY
        verify(repositorio).delete(veterinario);
    }

    @Test
    @Order(2)
    public void deveRetornarVeterinario_quandoBuscarPorIdExistente() {

        // ARRANGE
        Integer idExistente = 1;

        Veterinario veterinarioEsperado = new Veterinario(
                idExistente,
                "Erica Queiroz Pinto",
                "",
                "",
                BigDecimal.ZERO);

        when(repositorio.findById(idExistente))
                .thenReturn(Optional.of(veterinarioEsperado));

        // ACT
        Optional<Veterinario> resposta = service.buscaVeterinariosPeloId(idExistente);

        // ASSERT
        assertTrue(resposta.isPresent());

        assertEquals(
                "Erica Quei",
                resposta.get().getNome());

        verify(repositorio).findById(idExistente);
    }

    //Parte 1 - Exercicio A5
    @Test
    @Order(4)
    public void deveRetornarListaComDoisVeterinarios_quandoBuscarParteDoNome() {

        // ARRANGE
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

        // MOCK
        when(repositorio.findByNomeContains(parteNome)).thenReturn(listaEsperada);

        // ACT
        List<Veterinario> resultado = service.buscaVeterinariosComParteNome(parteNome);

        // ASSERT
        assertEquals(2, resultado.size());

        // VERIFY
        verify(repositorio).findByNomeContains(parteNome);
    }

    @Test
    @Order(5)
    @SuppressWarnings("null")
    public void deveLancarExcecaoAoApagar_quandoIdNaoExistir() {

        // ARRANGE
        Integer idInexistente = 999;

        when(repositorio.findById(idInexistente)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(RuntimeException.class, () -> {
            service.apagarPorId(idInexistente);
        });

        // VERIFY
        verify(repositorio, never()).delete((Veterinario) any());
    }


}
