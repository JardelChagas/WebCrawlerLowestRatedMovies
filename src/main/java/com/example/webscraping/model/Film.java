package com.example.webscraping.model;

import java.util.List;

public class Film {

    private String name;
    private float note;
    private List<String> directors;
    private List<String> mainCast;
    private List<String> titleComment;
    private List<String> positiveComment;

    public String getTitleComment() {
        String titleComment = "";
        if(this.titleComment != null)
            for(String tc: this.titleComment){
                if(titleComment.equals(""))
                    titleComment = tc;
                else
                    titleComment += ", "+tc;
            }
        return titleComment;
    }

    public void setTitleComment(List<String> titleComment) {
        this.titleComment = titleComment;
    }




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
        if(this.directors != null)
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

    public String getMainCast() {
        String mainCast = "";
        if(this.mainCast != null)
            for(String mc: this.mainCast){
                if(mainCast.equals(""))
                    mainCast = mc;
                else
                    mainCast += ", "+mc;
            }

        return mainCast;
    }

    public void setMainCast(List<String> mainCast) {
        this.mainCast = mainCast;
    }

    public String getPositiveComment() {
        String positiveComment = "";
        if(this.positiveComment != null)
            for(String pc: this.positiveComment){
                if(positiveComment.equals(""))
                    positiveComment = pc;
                else
                    positiveComment += ", "+pc;
            }
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
