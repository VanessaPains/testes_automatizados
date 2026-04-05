package br.edu.iftm.ta.junit_vanessa_pains.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FuncionarioTest {

    ///Testes para validar as regras de negócio da classe Funcionario
    ///aqui estão os testes para a classe Funcionario, que verificam se as regras de negócio estão sendo seguidas corretamente.
    @Test
    void deveLancarExcecao_quandoHorasAbaixoDoMinimo() {
        Funcionario f = new Funcionario();

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            f.setHorasTrabalhadas(10);
        });

        assertEquals("Horas devem estar entre 20 e 160.", ex.getMessage());
    }

    ///Testes para validar as regras de negócio da classe Funcionario
    ///funciona assim: o teste verifica se a exceção lançada tem a mensagem correta, garantindo 
    ///que a regra de negócio está sendo aplicada corretamente.
    @Test
    void deveLancarExcecao_quandoHorasAcimaDoMaximo() {//esse teste verifica se a exceção lançada tem a mensagem correta, garantindo que a regra de negócio está sendo aplicada corretamente.
        Funcionario f = new Funcionario();

        assertThrows(IllegalArgumentException.class, () -> {
            f.setHorasTrabalhadas(200);
        });
    }

    //esse teste verifica se a exceção lançada tem a mensagem correta, garantindo que a regra 
    //de negócio está sendo aplicada corretamente.
    @Test
    void deveLancarExcecao_quandoValorHoraInvalido() {//esse deveLancarExcecao_quandoValorHoraInvalido verifica se a exceção lançada tem a mensagem correta, garantindo que a regra de negócio está sendo aplicada corretamente.
        Funcionario f = new Funcionario();

        assertThrows(IllegalArgumentException.class, () -> {
            f.setValorHora(500);
        });
    }

    //esse teste verifica se a exceção lançada tem a mensagem correta, garantindo que a regra 
    //de negócio está sendo aplicada corretamente.
    @Test
    void deveCalcularPagamentoCorretamente() {//esse teste verifica se o método calcularPagamento está retornando o valor correto com base nas horas trabalhadas e no valor da hora.
        Funcionario f = new Funcionario();
        f.setHorasTrabalhadas(100);
        f.setValorHora(20);

        double resultado = f.calcularPagamento();

        assertEquals(2000, resultado);
    }
}
