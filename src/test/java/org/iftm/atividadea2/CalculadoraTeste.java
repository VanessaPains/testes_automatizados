package org.iftm.atividadea2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadoraTeste {

    private static Calculadora calculadora;

    @BeforeAll
    static void setupPrincipal() {
        System.out.println("Iniciando testes da Calculadora...");
    }

    @BeforeEach
    void setup() {
        calculadora = new Calculadora();
    }

    @AfterEach
    void fim() {
        System.out.println("Fim do teste.");
    }

    /*
     * TESTE DE SOMA
     * Cenário: Memória inicia em 1
     * Somar 5
     * Resultado esperado: 6
     */
    @Test
    @DisplayName("Teste de soma")
    public void testeSomarValor() {

        // Arrange
        int valorEntrada = 5;
        int resultadoEsperado = 6;

        // Act
        calculadora.somar(valorEntrada);
        int resultadoReal = calculadora.getMemoria();

        // Assert
        assertEquals(resultadoEsperado, resultadoReal);
    }

    /*
     * TESTE DE SUBTRAÇÃO
     * Memória inicia em 1
     * Subtrair 1
     * Resultado esperado: 0
     */
    @Test
    @DisplayName("Teste de subtração")
    public void testeSubtrairValor() {

        // Arrange
        int valorEntrada = 1;
        int resultadoEsperado = 0;

        // Act
        calculadora.subtrair(valorEntrada);
        int resultadoReal = calculadora.getMemoria();

        // Assert
        assertEquals(resultadoEsperado, resultadoReal);
    }

    /*
     * TESTE DE MULTIPLICAÇÃO
     * Memória inicia em 1
     * Multiplicar por 5
     * Resultado esperado: 5
     */
    @Test
    @DisplayName("Teste de multiplicação")
    public void testeMultiplicarValor() {

        // Arrange
        int valorEntrada = 5;
        int resultadoEsperado = 5;

        // Act
        calculadora.multiplicar(valorEntrada);
        int resultadoReal = calculadora.getMemoria();

        // Assert
        assertEquals(resultadoEsperado, resultadoReal);
    }

    /*
     * TESTE DE DIVISÃO
     * Memória inicia em 10
     * Dividir por 5
     * Resultado esperado: 2
     */
    @Test
    @DisplayName("Teste de divisão")
    public void testeDividirValor() {

        // Arrange
        calculadora = new Calculadora(10);
        int valorEntrada = 5;
        int resultadoEsperado = 2;

        // Act
        calculadora.dividir(valorEntrada);
        int resultadoReal = calculadora.getMemoria();

        // Assert
        assertEquals(resultadoEsperado, resultadoReal);
    }

    /*
     * TESTE DIVISÃO POR ZERO
     */
    @Test
    @DisplayName("Teste divisão por zero")
    public void testeDivisaoPorZero() {

        // Arrange
        int valorEntrada = 0;

        // Act / Assert
        assertThrows(ArithmeticException.class, () -> {
            calculadora.dividir(valorEntrada);
        });
    }

    @Test
    @DisplayName("Teste de exponenciação com valor válido")
    public void testeExponenciacao() {

        // Arrange
        calculadora = new Calculadora(2); // memória = 2
        int expoente = 2;
        int resultadoEsperado = 4; // 2² = 4

        // Act
        calculadora.exponenciar(expoente);
        int resultadoReal = calculadora.getMemoria();

        // Assert
        assertEquals(resultadoEsperado, resultadoReal);
    }
}
