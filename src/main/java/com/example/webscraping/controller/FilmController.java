package com.example.webscraping.controller;

import com.example.webscraping.domain.WebCrawler;
import com.example.webscraping.model.Film;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {
    @GetMapping
    public ModelAndView home(){

        WebCrawler ws = new WebCrawler("https://www.imdb.com/chart/bottom");
        List<Film> films = ws.getElements();
        ModelAndView mv = new ModelAndView("films");

        mv.addObject("films",films);

        return mv;
    }
}
