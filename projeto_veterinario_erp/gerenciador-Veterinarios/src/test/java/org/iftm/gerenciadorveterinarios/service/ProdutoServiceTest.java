package org.iftm.gerenciadorveterinarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.iftm.gerenciadorveterinarios.entities.Produto;
import org.iftm.gerenciadorveterinarios.repositories.ProdutoRepository;
import org.iftm.gerenciadorveterinarios.servicies.ProdutoService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    //Parte 2 - Exercicio A5
    @Test
    @Order(1)
    public void deveCadastrarProdutoComStatusAtivo() {

        // ARRANGE
        Produto produto = new Produto(
                1,
                "Ração Premium",
                BigDecimal.valueOf(150),
                10,
                false);

        Produto produtoSalvo = new Produto(
                1,
                "Ração Premium",
                BigDecimal.valueOf(150),
                10,
                true);

        when(repository.save(any())).thenReturn(produtoSalvo);

        // ACT
        Produto resultado = service.cadastrar(produto);

        // ASSERT
        assertTrue(resultado.isAtivo());

        verify(repository).save(any());
    }
}