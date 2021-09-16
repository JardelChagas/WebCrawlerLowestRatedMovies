package com.example.webscraping.model;

import java.util.List;

public class Filme {
    public  Filme(){}
    public  Filme(String name, float note, List<String> directors, List<String> mainCast, List<String> positiveComment){
        this.name = name;
        this.note = note;
        this.directors = directors;
        this.mainCast = mainCast;
        this.positiveComment = positiveComment;
    }

    private String name;
    private float note;
    private List<String> directors;
    private List<String> mainCast;
    private List<String> positiveComment;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getDirectors() {
        String directors = "";
        for (String d: this.directors){
            if(directors.equals(""))
                directors = d;
            else
                directors += ", " + d;
        }

        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public void setMainCast(List<String> mainCast) {
        this.mainCast = mainCast;
    }

    public List<String> getPositiveComment() {
        return positiveComment;
    }

    public void setPositiveComment(List<String> positiveComment) {
        this.positiveComment = positiveComment;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "nome='" + name + '\'' +
                ", nota=" + note +
                ", diretores=" + directors +
                ", elencoPrincipal=" + mainCast +
                ", comentarioPositivo=" + positiveComment +
                "}";
    }
}
