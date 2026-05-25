package org.iftm.gerenciadorveterinarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Funcionario;
import org.iftm.gerenciadorveterinarios.repositories.FuncionarioRepository;
import org.iftm.gerenciadorveterinarios.servicies.FuncionarioService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository repository;

    @InjectMocks
    private FuncionarioService service;

    // Parte 2 - Exercicio A5
    @Test
    @Order(1)
    public void deveCadastrarFuncionarioForaDeFerias() {

        // ARRANGE
        Funcionario funcionario = new Funcionario(
                1,
                "Vanessa",
                "Desenvolvedora",
                BigDecimal.valueOf(5000),
                true);

        Funcionario funcionarioSalvo = new Funcionario(
                1,
                "Vanessa",
                "Desenvolvedora",
                BigDecimal.valueOf(5000),
                false);

        // MOCK
        when(repository.save(any())).thenReturn(funcionarioSalvo);

        // ACT
        Funcionario resultado = service.cadastrar(funcionario);

        // ASSERT
        assertFalse(resultado.isEmFerias());

        // VERIFY
        verify(repository).save(any());
    }

    @Test
    @Order(2)
    public void deveLancarExcecao_quandoSalarioForMenorQueMinimo() {

        // ARRANGE
        Funcionario funcionario = new Funcionario(
                1,
                "Vanessa",
                "Desenvolvedora",
                BigDecimal.valueOf(1000),
                false);

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrar(funcionario);
        });

        // VERIFY
        verify(repository, never()).save(any());
    }


    @Test
public void deveConcederFeriasParaFuncionario() {

    // ARRANGE
    Integer id = 1;

    Funcionario funcionario =
            new Funcionario(
                    id,
                    "Vanessa",
                    "Desenvolvedora",
                    BigDecimal.valueOf(5000),
                    false);

    // MOCK
    when(repository.findById(id)).thenReturn(Optional.of(funcionario));

    // MOCK
    when(repository.save(any())).thenReturn(funcionario);

    // ACT
    service.concederFerias(id);

    // ASSERT
    assertTrue(funcionario.isEmFerias());

    // VERIFY
    verify(repository)
            .save(funcionario);
}
}
