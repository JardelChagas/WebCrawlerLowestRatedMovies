package com.example.webscraping.model;

import java.util.List;

public class Filme {
    public  Filme(){}
    public  Filme(String nome, float nota, List<String> diretores, List<String> elencoPrincipal, List<String> comentarioPositivo){
        this.nome = nome;
        this.nota = nota;
        this.diretores = diretores;
        this.elencoPrincipal = elencoPrincipal;
        this.comentarioPositivo = comentarioPositivo;
    }
    public  Filme(String nome){this.nome = nome;}

    private String nome;
    private float nota;
    private List<String> diretores;
    private List<String> elencoPrincipal;
    private List<String> comentarioPositivo;



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public List<String> getDiretores() {
        return diretores;
    }

    public void setDiretores(List<String> diretores) {
        this.diretores = diretores;
    }

    public List<String> getElencoPrincipal() {
        return elencoPrincipal;
    }

    public void setElencoPrincipal(List<String> elencoPrincipal) {
        this.elencoPrincipal = elencoPrincipal;
    }

    public List<String> getComentarioPositivo() {
        return comentarioPositivo;
    }

    public void setComentarioPositivo(List<String> comentarioPositivo) {
        this.comentarioPositivo = comentarioPositivo;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "nome='" + nome + '\'' +
                ", nota=" + nota +
                ", diretores=" + diretores +
                ", elencoPrincipal=" + elencoPrincipal +
                ", comentarioPositivo=" + comentarioPositivo +
                "}\n";
    }
}
