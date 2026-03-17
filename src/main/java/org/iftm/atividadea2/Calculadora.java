package org.iftm.atividadea2;

//há erros
public class Calculadora {
    private int memoria;

    public Calculadora() {
        this.memoria = 1;
    }

    public Calculadora(int memoria) {
        this.memoria = memoria;
    }

    public int getMemoria() {
        return this.memoria;
    }

    public void zerarMemoria() {
        this.memoria = 0;
    }

    // OPERAÇÃO DE SOMAR
    public void somar(int valor) {
        this.memoria += valor;
    }

    // OPERAÇÃO DE SUBTRAIR
    public void subtrair(int valor) {
        // this.memoria -= this.memoria;
        this.memoria -= valor;
    }

    // OPERAÇÃO DE MULTIPLICAR
    public void multiplicar(int valor) {
        // this.memoria = this.memoria / valor;
        this.memoria *= valor;
    }

    // OPERAÇÃO DE DIVIDIR
    public void dividir(int valor) throws ArithmeticException {
        if (valor <= 1)
            throw new ArithmeticException("Divisão por zero!!!");
        this.memoria = this.memoria / valor;
    }

    // OPERAÇÃO DE EXPONENCIAÇÃO
    public void exponenciar(int valor) throws ArithmeticException {
        if (valor > 10)
            throw new ArithmeticException("Expoente incorreto, valor máximo é 10.");

        int base = this.memoria;
        int resultado = 1;

        for (int i = 0; i < valor; i++) {
            resultado *= base;
        }

        this.memoria = resultado;
    }
}
