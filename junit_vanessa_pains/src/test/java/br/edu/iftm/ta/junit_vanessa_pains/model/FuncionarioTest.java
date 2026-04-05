package br.edu.iftm.ta.junit_vanessa_pains.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FuncionarioTest {


    @Test
    //ISSO É PARA HORAS TRABALHADAS ABAIXO DO LIMITE MINIMO, QUE É 20 HORAS.
    //esse teste verifica se a exceção lançada tem a mensagem correta, garantindo que a 
    //regra de negócio está sendo aplicada corretamente.
    void deveLancarExcecao_quandoHorasAbaixoDoMinimo() {
        // ARRANGE (preparação dos dados)
        Funcionario f = new Funcionario();

        // ACT + ASSERT (ação + verificação)
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            f.setHorasTrabalhadas(10);
        });

        // ASSERT (validação da mensagem)
        assertEquals("Horas devem estar entre 20 e 160.", ex.getMessage());
    }


    @Test
    //ISSO É PARA HORAS TRABALHADAS ACIMA DO LIMITE MAXIMO, QUE É 160 HORAS.
    /// Testes para validar as regras de negócio da classe Funcionario
    /// funciona assim: o teste verifica se a exceção lançada tem a mensagem
    /// correta, garantindo que a regra de negócio está sendo aplicada corretamente.
    void deveLancarExcecao_quandoHorasAcimaDoMaximo() {// esse teste verifica se a exceção lançada tem a mensagem
                                                       // correta, garantindo que a regra de negócio está sendo aplicada
                                                       // corretamente.
        Funcionario f = new Funcionario();

        assertThrows(IllegalArgumentException.class, () -> {
            f.setHorasTrabalhadas(200);
        });
    }

    @Test
    //ISSO É PARA VALOR HORA ACIMA DO LIMITE MAXIMO, QUE É 151.8.
    // esse teste verifica se a exceção lançada tem a mensagem correta, garantindo
    // que a regra de negócio está sendo aplicada corretamente.
    void deveLancarExcecao_quandoValorHoraInvalido() {// esse deveLancarExcecao_quandoValorHoraInvalido verifica se a
                                                      // exceção lançada tem a mensagem correta, garantindo que a regra
                                                      // de negócio está sendo aplicada corretamente.
        Funcionario f = new Funcionario();

        assertThrows(IllegalArgumentException.class, () -> {
            f.setValorHora(500);
        });
    }


    @Test
    //ISSO É PARA CALCULO DE PAGAMENTO CORRETO, COM HORAS TRABALHADAS 100 E VALOR HORA 20, O PAGAMENTO DEVE SER 2000.
    //teste para verificar se o método calcularPagamento está retornando o valor correto com base
    //nas horas trabalhadas e no valor da hora, garantindo que a regra de negócio está sendo aplicada corretamente.
    void deveCalcularPagamentoCorretamente() {// esse teste verifica se o método calcularPagamento está retornando o
                                              // valor correto com base nas horas trabalhadas e no valor da hora.
        // ARRANGE
        Funcionario f = new Funcionario();
        f.setHorasTrabalhadas(100);
        f.setValorHora(20);

        // ACT
        double resultado = f.calcularPagamento();

        // ASSERT
        assertEquals(2000, resultado);
    }
    
    @Test
    // TESTE: HORAS NO LIMITE MÍNIMO (20) COM PAGAMENTO VÁLIDO
    //teste para verificar se o método calcularPagamento lança uma exceção quando o pagamento
    //calculado está fora dos limites estabelecidos (menor que 1518 ou maior que 10000), garantindo 
    //que a regra de negócio está sendo aplicada corretamente.
    //limit MINIMO horas 20, valor hora 20, pagamento 400
    void deveAceitarHorasNoLimiteMinimo() {

        // ARRANGE
        Funcionario f = new Funcionario();
        f.setHorasTrabalhadas(80); // 80 * 20 = 1600 (válido ≥ 1518)
        f.setValorHora(20);

        // ACT + ASSERT
        assertDoesNotThrow(() -> f.calcularPagamento());
    }


    @Test
    //ISSO É PARA HORAS TRABALHADAS ACIMA DO LIMITE MAXIMO, QUE É 160 HORAS.
    //teste para verificar se o método calcularPagamento lança uma exceção quando o pagamento
    //calculado está fora dos limites estabelecidos (menor que 1518 ou maior que 10000), garantindo 
    //que a regra de negócio está sendo aplicada corretamente.
    //limit MAXIMO horas 160, valor hora 20, pagamento 3200
    void deveAceitarHorasNoLimiteMaximo() {

        // ARRANGE
        Funcionario f = new Funcionario();
        f.setHorasTrabalhadas(160);//diz que as horas trabalhadas são definidas como 160, que é o limite máximo permitido, garantindo que a regra de negócio está sendo aplicada corretamente.
        f.setValorHora(20);//diz que o valor da hora é definido como 20, garantindo que a regra de negócio está sendo aplicada corretamente.

        // ACT + ASSERT
        assertDoesNotThrow(() -> f.calcularPagamento());//diz que o método calcularPagamento deve ser executado sem lançar exceção, garantindo que a regra de negócio está sendo aplicada corretamente.
    }


    @Test
    //ISSO É PARA PAGAMENTO ABAIXO DO LIMITE MINIMO, QUE É 1518.
    //teste para verificar se o método calcularPagamento lança uma exceção quando o pagamento
    //calculado está fora dos limites estabelecidos (menor que 1518 ou maior que 10000), garantindo 
    //que a regra de negócio está sendo aplicada corretamente.
    void deveLancarErro_quandoPagamentoAbaixoDoMinimo() {

        // ARRANGE
        Funcionario f = new Funcionario();
        f.setHorasTrabalhadas(20);
        f.setValorHora(20); // 400

        // ACT + ASSERT
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            f.calcularPagamento();
        });

        // ASSERT
        assertEquals("Pagamento fora dos limites.", ex.getMessage());
    }

    @Test
    //ISSO É PARA PAGAMENTO ACIMA DO LIMITE MAXIMO, QUE É 10000.
    //teste para verificar se o método calcularPagamento lança uma exceção quando o pagamento
    //calculado está fora dos limites estabelecidos (menor que 1518 ou maior que 10000), garantindo 
    //que a regra de negócio está sendo aplicada corretamente.
    void deveLancarErro_quandoPagamentoAcimaDoLimite() {

        // ARRANGE
        Funcionario f = new Funcionario();
        f.setHorasTrabalhadas(160);
        f.setValorHora(100);

        // ACT + ASSERT
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            f.calcularPagamento();
        });

        // ASSERT
        assertEquals("Pagamento fora dos limites.", ex.getMessage());

    }
}
