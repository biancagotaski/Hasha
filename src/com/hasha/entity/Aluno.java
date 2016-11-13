package com.hasha.entity;

/**
 * Created by Franclin Sousa on 09/11/2016.
 *
 * @author Franclin Sousa
 */
public class Aluno {

    private Integer id;
    private String nome;
    private Double nota1;
    private Double nota2;


    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }
}
