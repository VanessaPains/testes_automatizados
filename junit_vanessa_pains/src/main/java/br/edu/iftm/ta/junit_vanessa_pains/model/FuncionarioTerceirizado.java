package br.edu.iftm.ta.junit_vanessa_pains.model;

//essa classe FuncionarioTerceirizado é uma subclasse da classe Funcionario, e ela adiciona um atributo adicional chamado despesaAdicional, que representa uma despesa extra que o funcionário terceirizado pode ter. A classe também sobrescreve o método calcularPagamento para incluir a despesa adicional no cálculo do pagamento total do funcionário terceirizado.
public class FuncionarioTerceirizado extends Funcionario {

    //atributo privado (profissional) a despesa adicional é um valor extra que o
    //funcionário terceirizado pode ter, e o método setDespesaAdicional é responsável
    //por definir esse valor, garantindo que ele não ultrapasse o limite estabelecido.
    private double despesaAdicional;

    //esse metodo setDespesaAdicional é responsável por definir o valor da despesa adicional
    //para o funcionário terceirizado, e antes de atribuir o valor, ele verifica se a despesa
    //não ultrapassa o limite de 1000. Se a despesa for maior que 1000, ele lança
    //uma exceção IllegalArgumentException com uma mensagem de erro.
    public void setDespesaAdicional(double despesa) {
        if (despesa > 1000) {//se dispesa for maior que 1000, ele lança uma exceção IllegalArgumentException com uma mensagem de erro.
            throw new IllegalArgumentException("Despesa não pode ultrapassar 1000.");
        }
        this.despesaAdicional = despesa;//aqui diz que a despesa adicional é atribuída ao atributo despesaAdicional, garantindo que a regra de negócio está sendo aplicada corretamente.
    }

    //aqui o método calcularPagamento é sobrescrito para incluir a despesa adicional no
    //cálculo do pagamento total do funcionário terceirizado. Ele chama o método calcularPagamento
    //da classe pai (super.calcularPagamento()) para obter o pagamento base, e depois
    //adiciona a despesa adicional multiplicada por 1.1 (para incluir um bônus de 10%).
    //Se o pagamento total exceder 10000, ele lança uma exceção IllegalArgumentException com uma mensagem de erro.
    @Override
    public double calcularPagamento() {

        //aqui diz que o pagamento base é calculado usando o método calcularPagamento
        //da classe pai (super.calcularPagamento()), e depois o total é calculado somando
        //o pagamento base com a despesa adicional multiplicada por 1.1, garantindo que a
        //regra de negócio está sendo aplicada corretamente.
        double pagamentoBase = super.calcularPagamento();
        double total = pagamentoBase + (despesaAdicional * 1.1);

        //se o pagamento total exceder 10000, ele lança uma exceção IllegalArgumentException 
        //com uma mensagem de erro.
        if (total > 10000) {
            throw new IllegalArgumentException("Pagamento total excede limite.");
        }

        return total;//vai retornar o valor total do pagamento, garantindo que a regra de negócio está sendo aplicada corretamente.
    }
}
