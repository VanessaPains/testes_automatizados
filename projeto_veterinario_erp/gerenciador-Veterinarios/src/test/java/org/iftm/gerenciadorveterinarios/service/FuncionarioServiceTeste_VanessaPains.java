package org.iftm.gerenciadorveterinarios.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.iftm.gerenciadorveterinarios.entities.Funcionario;
import org.iftm.gerenciadorveterinarios.servicies.FuncionarioService;
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
public class FuncionarioServiceTeste_VanessaPains {

    @Autowired
    private FuncionarioService service;

    //teste real para validar o cadastro de um funcionário com férias desativadas quando o salário for válido, usando a service real e o banco de teste, sem mocks.
    @Test
    @Order(1)
    @DisplayName("deve cadastrar um funcionário com férias desativadas quando o salário for válido")
    public void deveCadastrarFuncionarioForaDeFerias() {
        // ARRANGE
        Funcionario funcionario = new Funcionario(
                null,
                "Vanessa",
                "Desenvolvedora",
                BigDecimal.valueOf(5000),
                true);

        // ACT
        Funcionario resultado = service.cadastrar(funcionario);

        // ASSERT
        assertNotNull(resultado, "O funcionário salvo não deve ser nulo.");
        assertFalse(resultado.isEmFerias(), "O funcionário não deve estar de férias após o cadastro.");
        assertNotNull(resultado.getId(), "O funcionário deve receber um ID ao ser persistido.");
        assertEquals("Desenvolvedora", resultado.getCargo());
    }

    //teste real para validar o comportamento de erro ao tentar cadastrar um funcionário com salário abaixo do mínimo, usando a service real e o banco de teste, sem mocks.
    @Test
    @Order(2)
    @DisplayName("deve lançar exceção quando o salário do funcionário for menor que o mínimo")
    public void deveLancarExcecao_quandoSalarioForMenorQueMinimo() {
        // ARRANGE
        Funcionario funcionario = new Funcionario(
                null,
                "Vanessa",
                "Desenvolvedora",
                BigDecimal.valueOf(1000),
                false);

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(funcionario));
    }

    //teste real para validar a concessão de férias para um funcionário existente, usando a service real e o banco de teste, sem mocks.
    @Test
    @Order(3)
    @DisplayName("deve conceder férias para funcionário existente")
    public void deveConcederFeriasParaFuncionario() {
        // ARRANGE
        Funcionario funcionario = new Funcionario(
                null,
                "Vanessa",
                "Desenvolvedora",
                BigDecimal.valueOf(5000),
                false);

        Funcionario funcionarioSalvo = service.cadastrar(funcionario);

        // ACT
        Funcionario resultado = service.concederFerias(funcionarioSalvo.getId());

        // ASSERT
        assertNotNull(resultado, "O resultado não deve ser nulo ao conceder férias.");
        assertTrue(resultado.isEmFerias(), "O funcionário deve estar de férias após a concessão.");
        assertEquals(funcionarioSalvo.getId(), resultado.getId());
    }

    //teste real para validar o comportamento de erro ao tentar conceder férias para um funcionário que não existe, usando a service real e o banco de teste, sem mocks.
    @Test
    @Order(4)
    @DisplayName("deve lançar exceção quando não encontrar funcionário para conceder férias")
    public void deveLancarExcecao_quandoFuncionarioNaoExistirAoConcederFerias() {
        // ACT + ASSERT
        assertThrows(RuntimeException.class, () -> service.concederFerias(999));
    }
}
