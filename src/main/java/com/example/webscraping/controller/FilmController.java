package com.example.webscraping.controller;

import com.example.webscraping.domain.WebScraping;
import com.example.webscraping.model.Film;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {
    @GetMapping
    public ModelAndView home(){

        WebScraping ws = new WebScraping("https://www.imdb.com/chart/bottom");
        List<Film> films = ws.getElements();
        ModelAndView mv = new ModelAndView("films");

        mv.addObject("films",films);

        return mv;
    }
}
