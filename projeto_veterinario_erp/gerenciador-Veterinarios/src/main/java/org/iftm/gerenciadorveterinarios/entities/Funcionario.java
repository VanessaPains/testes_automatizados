package org.iftm.gerenciadorveterinarios.entities;

import java.math.BigDecimal;

public class Funcionario {

    private Integer id;
    private String nome;
    private String cargo;
    private BigDecimal salario;
    private boolean emFerias;

    public Funcionario() {
    }

    public Funcionario(Integer id,
            String nome,
            String cargo,
            BigDecimal salario,
            boolean emFerias) {

        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
        this.emFerias = emFerias;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public boolean isEmFerias() {
        return emFerias;
    }

    public void setEmFerias(boolean emFerias) {
        this.emFerias = emFerias;
    }
}