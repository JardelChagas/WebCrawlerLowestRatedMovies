package com.example.webscraping.domain;

import com.example.webscraping.model.Filme;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
        System.out.println(hrefs);
        for(int i=0; i < 10; i++){
            Filme f = new Filme();
            f.setName(getName(hrefs.get(i)));

            List<Element> att = trFilms.get(i).getElementsByTag("td");
            f.setNote(Float.parseFloat(att.get(2).text()));

            f.setDirectors(getDirector(hrefs.get(i)));
            f.setMainCast(getMainCast(hrefs.get(i)));
            getPositiveComment(hrefs.get(i));
            //f.setPositiveComment();reviews-header
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
        Collections.reverse(hrefs);
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

    private List<String> getDirector(String url){
        List<String> diretores = new ArrayList<>();

        Document d = this.createNewDoc(url);
        Element ul = d.getElementsByClass("ipc-metadata-list ipc-metadata-list--dividers-all StyledComponents__CastMetaDataList-y9ygcu-10 cbPPkN ipc-metadata-list--base").first();
        Element div = ul.getElementsByTag("div").first();
        Elements ul2 = div.getElementsByTag("li");

        for(Element u: ul2){
            diretores.add(u.text());
        }

        return diretores;
    }

    private List<String> getMainCast(String url) {
        List<String> mainCast = new ArrayList<>();
        Document d = this.createNewDoc(url);
        //Elements section = d.getElementsByClass("ipc-sub-grid ipc-sub-grid--page-span-2 ipc-sub-grid--wraps-at-above-l ipc-shoveler__grid");//select("section[data-testid='title-cast']").first();
        Elements a = d.select("a[data-testid='title-cast-item__actor']");//.first();
        a.forEach(b->{
            mainCast.add(b.text());
        });
        System.out.println("----------------------------");
        return mainCast;
    }

    private List<String> getPositiveComment(String url) {
        List<String> positiveComment = new ArrayList<>();
        Document d = this.createNewDoc(url);
        Element e = d.select("div[data-testid='reviews-header']").first();
        Element f = e.getElementsByTag("a").first();

        System.out.println("https://www.imdb.com"+f.attr("href"));

        return positiveComment;
    }













    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
