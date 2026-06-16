package org.iftm.gerenciadorveterinarios.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String especie;
    private Integer idade;
    private boolean internado;

    public Animal() {
    }

    public Animal(Integer id,
            String nome,
            String especie,
            Integer idade,
            boolean internado) {

        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
        this.internado = internado;
    }

    public Integer getId() {
        return id;
    }

    public boolean isInternado() {
        return internado;
    }

    public void setInternado(boolean internado) {
        this.internado = internado;
    }

    public String getEspecie() {
        return especie;
    }
}