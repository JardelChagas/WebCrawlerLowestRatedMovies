package com.example.webscraping.domain;

import com.example.webscraping.model.Filme;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.w3c.dom.html.HTMLFormElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WebScraping{
    private String url;
    private Document doc;

    public WebScraping(String url){
        this.url = url;
        this.doc = createNewDoc(this.url);
    }

    public List<Filme> getElements() throws IOException {

        Element tableofFilm =  this.doc.getElementsByClass("chart full-width").first();
        Element tbodyofFilm = tableofFilm.getElementsByTag("tbody").first();
        List<Element> trFilms = tbodyofFilm.getElementsByTag("tr");
        List<Filme> films = new ArrayList<>();

        List<String> hrefs = getListOfLinkTop10(trFilms);
        for(int i=9; i >= 0; i--){

            Filme f = new Filme();
            final int j = i;

            Thread t1 = new Thread(() -> {
                    f.setName(getName(hrefs.get(j)));
            });

            Thread t2 = new Thread(() -> {
                List<Element> att = trFilms.get(j).getElementsByTag("td");
                f.setNote(Float.parseFloat(att.get(2).text()));
            });

            Thread t3 = new Thread(() -> {
                    f.setDirectors(getDirector(hrefs.get(j)));
            });

            Thread t4 = new Thread(() -> {
                    f.setMainCast(getMainCast(hrefs.get(j)));
            });

            Thread t5 = new Thread(() -> {
                    try {
                        List<List<String>> ll = getPositiveComment(hrefs.get(j));
                        f.setPositiveComment(ll.get(1));
                        f.setTitleComment(ll.get(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();

            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
                t5.join();
                films.add(f);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return films;
    }


     List<String> getListOfLinkTop10(List<Element> trFilms){
        List<String> hrefs = new ArrayList<>();
        for(int i=0; i < 10; i++){
            Element e = trFilms.get(i).getElementsByTag("a").first();
            hrefs.add("https://www.imdb.com"+e.attr("href"));
        }
        //Collections.reverse(hrefs);
        return hrefs;
    }

     Document createNewDoc(String url){
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

     String getLinkElement(String url) {
        Document doc = createNewDoc(url);
        Element li = doc.select("li[data-testid='title-details-akas']").first();
        Element a = li.getElementsByTag("a").first();

        return "https://www.imdb.com"+a.attr("href");
    }



    String getName(String url){
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

    List<String> getDirector(String url){
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

    List<String> getMainCast(String url) {
        List<String> mainCast = new ArrayList<>();
        Document d = this.createNewDoc(url);
        //Elements section = d.getElementsByClass("ipc-sub-grid ipc-sub-grid--page-span-2 ipc-sub-grid--wraps-at-above-l ipc-shoveler__grid");//select("section[data-testid='title-cast']").first();
        Elements a = d.select("a[data-testid='title-cast-item__actor']");//.first();
        a.forEach(b->{
            mainCast.add(b.text());
        });
        return mainCast;
    }

    private List<List<String>> getPositiveComment(String url) throws IOException {
        List<String> positiveComment = new ArrayList<>();
        List<String> titleComment = new ArrayList<>();
        List<List<String>> t= new ArrayList<>();
        Document d = this.createNewDoc(url);
        Element userReviews = d.select("div[data-testid='reviews-header']").first();
        Element LinkComment = userReviews.getElementsByTag("a").first();
        String urlComment = "https://www.imdb.com"+LinkComment.attr("href").split("\\?")[0];
        urlComment +="?sort=userRating&dir=desc&ratingFilter=0";
        d = this.createNewDoc(urlComment);
        Element mainDiv = d.getElementsByClass("lister-item-content").first();
        Element title = mainDiv.getElementsByClass("title").first();
        Element content = mainDiv.getElementsByClass("content").first();
        positiveComment.add(content.text());
        titleComment.add(title.text());
        //System.out.println(title.text());

        /*final WebClient wc = new WebClient();
        wc.getOptions().setJavaScriptEnabled(false);
        wc.getOptions().setCssEnabled(false);
        wc.getOptions().setThrowExceptionOnScriptError(false);
        final HtmlPage page = wc.getPage(coment);
        //HtmlElement button = page.
        HtmlSelect select = page.getElementByName("sort");
        HtmlOption option = select.getOptionByValue("userRating");
        select.setSelectedAttribute(option,true).getUrl();

        page.executeJavaScript("this.form.submit()");
        System.out.println(select.setSelectedAttribute(option,true).getUrl());







        int i=0;
        System.out.println(coment);
        d = this.createNewDoc(coment);


        Elements star = d.getElementsByClass("ipl-ratings-bar");
        for(Element s :star){
            if(Integer.parseInt(s.getElementsByTag("span").get(1).text())>=5){
                i+=1;
                positiveComment.add(s.getElementsByTag("span").get(1).text());
            }
            if(i==2)
                break;
        }
        //HTMLFormElement

         */
        t.add(titleComment);
        t.add(positiveComment);
        return t;
    }

}
