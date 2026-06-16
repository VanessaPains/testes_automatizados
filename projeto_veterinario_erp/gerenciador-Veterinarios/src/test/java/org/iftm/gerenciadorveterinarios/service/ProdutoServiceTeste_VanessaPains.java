package org.iftm.gerenciadorveterinarios.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.iftm.gerenciadorveterinarios.entities.Produto;
import org.iftm.gerenciadorveterinarios.servicies.ProdutoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProdutoServiceTeste_VanessaPains {

    @Autowired
    private ProdutoService service;

    @Test
    @Order(1)
    @DisplayName("deve cadastrar um produto com status ativo quando o preço for válido")
    public void deveCadastrarProdutoComStatusAtivo() {
        // ARRANGE
        Produto produto = new Produto(
                null,
                "Ração Premium",
                BigDecimal.valueOf(150),
                10,
                false);

        // ACT
        Produto resultado = service.cadastrar(produto);

        // ASSERT
        assertNotNull(resultado, "O produto salvo não deve ser nulo.");
        assertTrue(resultado.isAtivo(), "O produto deve ser marcado como ativo após cadastro.");
        assertNotNull(resultado.getId(), "O produto deve receber um ID ao ser persistido.");
    }

    @Test
    @Order(2)
    @DisplayName("deve lançar exceção quando o preço do produto for negativo")
    public void deveLancarExcecao_quandoPrecoForNegativo() {
        // ARRANGE
        Produto produto = new Produto(
                null,
                "Coleira",
                BigDecimal.valueOf(-10),
                5,
                false);

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(produto));
    }

    @Test
    @Order(3)
    @DisplayName("deve inativar produto existente")
    public void deveInativarProduto_quandoExistir() {
        // ARRANGE
        Produto produto = new Produto(
                null,
                "Ração",
                BigDecimal.valueOf(50),
                20,
                true);

        Produto produtoSalvo = service.cadastrar(produto);

        // ACT
        Produto resultado = service.inativar(produtoSalvo.getId());

        // ASSERT
        assertNotNull(resultado, "O resultado não deve ser nulo ao inativar produto existente.");
        assertFalse(resultado.isAtivo(), "O produto deve ficar inativo após a inativação.");
        assertEquals(produtoSalvo.getId(), resultado.getId());
    }

    @Test
    @Order(4)
    @DisplayName("deve lançar exceção quando não encontrar produto para inativar")
    public void deveLancarExcecao_quandoProdutoNaoExistirAoInativar() {
        // ACT + ASSERT
        assertThrows(RuntimeException.class, () -> service.inativar(999));
    }
}
