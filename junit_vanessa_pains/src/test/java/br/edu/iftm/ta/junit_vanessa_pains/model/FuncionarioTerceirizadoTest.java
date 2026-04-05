package br.edu.iftm.ta.junit_vanessa_pains.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FuncionarioTerceirizadoTest {

    
    @Test
    //ISO É PARA DESPESA ADICIONAL ACIMA DO LIMITE PERMITIDO, QUE É 1000.
    void deveLancarExcecao_quandoDespesaAcimaDoLimite() {//verifica despeca adicional acima do limite permitido, garantindo que a regra de negócio está sendo aplicada corretamente.

        // ARRANGE
        FuncionarioTerceirizado f = new FuncionarioTerceirizado();

        // ACT
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            f.setDespesaAdicional(1500);
        });

        // ASSERT
        assertEquals("Despesa não pode ultrapassar 1000.", ex.getMessage());
    }
    

    @Test
    //ISSO É PARA CALCULO DE PAGAMENTO COM BONUS, COM HORAS TRABALHADAS 100, VALOR HORA 20 E DESPESA ADICIONAL 100, O PAGAMENTO DEVE SER 2000 + 110.
    void deveCalcularPagamentoComBonusCorretamente() {//verifica bonnus adicional no cálculo do pagamento, garantindo que a regra de negócio está sendo aplicada corretamente.
        
        // ARRANGE
        FuncionarioTerceirizado f = new FuncionarioTerceirizado();

        // ACT
        f.setHorasTrabalhadas(100);
        f.setValorHora(20);
        f.setDespesaAdicional(100);

        double resultado = f.calcularPagamento();

        // ASSERT
        assertEquals(2000 + 110, resultado);
    }

    @Test
    //ISSO É PARA VALOR HORA NEGATIVO, QUE É INVALIDO.
    //teste para verificar se o método setValorHora lança uma exceção quando o valor da
    //hora é negativo, garantindo que a regra de negócio está sendo aplicada corretamente.
    // TESTE: DESPESA NEGATIVA DEVE GERAR ERRO
    void deveLancarExcecao_quandoDespesaNegativa() {

        // ARRANGE
        FuncionarioTerceirizado f = new FuncionarioTerceirizado();

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            f.setDespesaAdicional(-10);
        });
    }


    @Test
    //ISSO É PARA CALCULO DE PAGAMENTO TOTAL ACIMA DO LIMITE PERMITIDO, QUE É 10000.
    void deveLancarExcecao_quandoPagamentoTotalUltrapassaLimite() {

        // ARRANGE
        FuncionarioTerceirizado f = new FuncionarioTerceirizado();
        f.setHorasTrabalhadas(160);
        f.setValorHora(60); // 9600
        f.setDespesaAdicional(500); // +550 = 10150

        // ACT
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            f.calcularPagamento();
        });

        // ASSERT
        assertEquals("Pagamento total excede limite.", ex.getMessage());
    }


    @Test
    //ISSO É PARA DESPESA ADICIONAL NO LIMITE PERMITIDO, QUE É 1000.
    void deveAceitarDespesaNoLimiteMaximo() {
        
        // ARRANGE
        FuncionarioTerceirizado f = new FuncionarioTerceirizado();

        // ACT
        f.setDespesaAdicional(1000);

        // ASSERT
        assertEquals(1000, f.getDespesaAdicional());
    }


    @Test
    //ISO É PARA CALCULO DE PAGAMENTO TOTAL DENTRO DO LIMITE PERMITIDO, QUE É 10000.
    void deveCalcularPagamentoDentroDoLimite() {

        // ARRANGE
        FuncionarioTerceirizado f = new FuncionarioTerceirizado();

        // ACT
        f.setHorasTrabalhadas(100);
        f.setValorHora(50); // 5000
        f.setDespesaAdicional(200); // +220 = 5220

        double resultado = f.calcularPagamento();

        // ASSERT
        assertTrue(resultado <= 10000);
        assertEquals("Dentro limite. " + resultado, "Dentro limite. " + resultado);
    }
}
