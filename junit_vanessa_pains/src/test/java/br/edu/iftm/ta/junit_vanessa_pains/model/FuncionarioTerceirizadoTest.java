package br.edu.iftm.ta.junit_vanessa_pains.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FuncionarioTerceirizadoTest {

    //Testes para validar as regras de negócio da classe FuncionarioTerceirizado
    @Test
    void deveLancarExcecao_quandoDespesaAcimaDoLimite() {//verifica despeca adicional acima do limite permitido, garantindo que a regra de negócio está sendo aplicada corretamente.
        FuncionarioTerceirizado f = new FuncionarioTerceirizado();

        assertThrows(IllegalArgumentException.class, () -> {
            f.setDespesaAdicional(2000);
        });
    }

    //Testes para validar as regras de negócio da classe FuncionarioTerceirizado
    @Test
    void deveCalcularPagamentoComBonus() {//verifica bonnus adicional no cálculo do pagamento, garantindo que a regra de negócio está sendo aplicada corretamente.
        FuncionarioTerceirizado f = new FuncionarioTerceirizado();
        f.setHorasTrabalhadas(100);
        f.setValorHora(20);
        f.setDespesaAdicional(100);

        double resultado = f.calcularPagamento();

        assertEquals(2000 + 110, resultado);
    }
}
