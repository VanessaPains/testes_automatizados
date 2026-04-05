package br.edu.iftm.ta.junit_vanessa_pains.model;

//essa classe Funcionario é uma classe que representa um funcionário, e ela possui atributos como nome, horas trabalhadas e valor da hora. A classe também possui métodos para definir as horas trabalhadas e o valor da hora, além de um método para calcular o pagamento do funcionário com base nessas informações. A classe inclui validações para garantir que as horas trabalhadas e o valor da hora estejam dentro de limites aceitáveis, e lança exceções quando essas regras de negócio são violadas.
public class Funcionario {

    //atributos privados (profissional)
    private String nome;
    private int horasTrabalhadas;
    private double valorHora;

    //metodos públicos (profissional) diz que as horas trabalhadas devem ser validadas antes de serem atribuídas 
    //ao atributo horasTrabalhadas, garantindo que a regra de negócio está sendo aplicada corretamente.
    //o setHorasTrabalhadas é responsável por definir as horas trabalhadas para o funcionário, e antes 
    //de atribuir as horas, ele chama o método validarHoras para garantir que as horas estão dentro dos limites estabelecidos.
    public void setHorasTrabalhadas(int horas) {
        validarHoras(horas);
        this.horasTrabalhadas = horas;
    }

    //metodos públicos (profissional). diz que o valor da hora deve ser validado antes de ser 
    //atribuído ao atributo valorHora, garantindo que a regra de negócio está sendo aplicada corretamente.
    //o setValorHora é responsável por definir o valor da hora para o funcionário, e antes de atribuir o 
    //valor, ele chama o método validarValorHora para garantir que o valor está dentro dos limites estabelecidos.
    public void setValorHora(double valor) {
        validarValorHora(valor);
        this.valorHora = valor;
    }

    //esse método calcularPagamento é responsável por calcular o pagamento do funcionário com base nas 
    //horas trabalhadas e no valor da hora, e também verifica se o pagamento está dentro dos limites estabelecidos.
    public double calcularPagamento() {
        double pagamento = horasTrabalhadas * valorHora;

        if (pagamento < 1518 || pagamento > 10000) {
            throw new IllegalArgumentException("Pagamento fora dos limites.");
        }

        return pagamento;//ele retorna o valor do pagamento calculado, garantindo que a regra de negócio está sendo aplicada corretamente.
    }

    //esse método validarHoras é responsável por validar se as horas trabalhadas estão dentro dos limites 
    //estabelecidos (entre 20 e 160 horas). Se as horas estiverem fora desses limites, ele lança uma exceção IllegalArgumentException com uma mensagem de erro.
    private void validarHoras(int horas) {
        if (horas < 20 || horas > 160) {
            throw new IllegalArgumentException("Horas devem estar entre 20 e 160.");
        }
    }

    //esse método validarValorHora é responsável por validar se o valor da hora está dentro dos limites 
    //estabelecidos (entre 15.18 e 151.8). Se o valor da hora estiver fora desses limites, ele lança uma exceção IllegalArgumentException com uma mensagem de erro.
    private void validarValorHora(double valor) {
        if (valor < 15.18 || valor > 151.8) {
            throw new IllegalArgumentException("Valor por hora inválido.");
        }
    }
}
