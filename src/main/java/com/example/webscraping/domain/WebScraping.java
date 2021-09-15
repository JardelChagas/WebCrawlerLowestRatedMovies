package com.example.webscraping.domain;

import com.example.webscraping.model.Filme;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WebScraping {
    private String url;
    private Document doc;

    public WebScraping(String url){
        this.url = url;
        this.doc = getDoc(this.url);

    }

    public List<Filme> getElements() throws IOException {

        Element tableofFilm =  this.doc.getElementsByClass("chart full-width").first();
        Element tbodyofFilm = tableofFilm.getElementsByTag("tbody").first();
        List<Element> trFilms = tbodyofFilm.getElementsByTag("tr");
        List<Filme> films = new ArrayList<>();

        List<String> hrefs = getListOfLinkTop10(trFilms);

        for(int i=0; i < 10; i++){
            Filme f = new Filme();

            Document doc = getDoc(hrefs.get(i));
            f.setNome(doc.getElementsByClass("OriginalTitle__OriginalTitleText-jz9bzr-0 llYePj").text());
            List<Element> att = trFilms.get(i).getElementsByTag("td");

           // att.get(1).text());
            f.setNota(Float.parseFloat(att.get(2).text()));
            films.add(f);
        }
        return films;
    }


    private List<String> getListOfLinkTop10(List<Element> trFilms){
        List<String> hrefs = new ArrayList<>();
        for(int i=0; i < 10; i++){
            Element e = trFilms.get(i).getElementsByTag("a").first();
            hrefs.add("https://www.imdb.com"+e.attr("href"));
        }
        return hrefs;
    }

    private Document getDoc(String url){
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
