package com.example.webscraping.domain;

import com.example.webscraping.model.Filme;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WebScraping {
    private String url;
    private Document doc;
    //private String t;
    public WebScraping(String url){
        this.url = url;
        this.doc = createNewDoc(this.url);

    }

    public List<Filme> getElements() throws IOException {

        Element tableofFilm =  this.doc.getElementsByClass("chart full-width").first();
        Element tbodyofFilm = tableofFilm.getElementsByTag("tbody").first();
        List<Element> trFilms = tbodyofFilm.getElementsByTag("tr");
        List<Filme> films = new ArrayList<>();


        String t="";
        List<String> hrefs = getListOfLinkTop10(trFilms);
        for(int i=0; i < 10; i++){
            Filme f = new Filme();
            List<String> diretores = new ArrayList<>();

            f.setNome(getName(hrefs.get(i)));


            Document d2 = this.createNewDoc(hrefs.get(i));
            Element ul = d2.getElementsByClass("ipc-metadata-list ipc-metadata-list--dividers-all StyledComponents__CastMetaDataList-y9ygcu-10 cbPPkN ipc-metadata-list--base").first();
            Element div = ul.getElementsByTag("div").first();
            Elements ul2 = div.getElementsByTag("li");

            for(Element u: ul2){
                diretores.add(u.text());

            }

            f.setDiretores(diretores);
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
            System.out.println("--"+hrefs.get(i)+"--");
        }
        return hrefs;
    }

    private Document createNewDoc(String url){
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getLinkElement(String url) {
        Document doc = createNewDoc(url);
        Element li = doc.select("li[data-testid='title-details-akas']").first();
        Element a = li.getElementsByTag("a").first();

        return "https://www.imdb.com"+a.attr("href");
    }

    private String getName(String url){
        Document d = this.createNewDoc(this.getLinkElement(url));
        Element table = d.getElementsByTag("table").last();
        Elements tr = table.getElementsByTag("tr");

        for(Element td: tr){
            if("UK".equals(td.getElementsByTag("td").first().text())){
                return td.getElementsByTag("td").last().text();
            }
        }
        return "";
    }




    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
