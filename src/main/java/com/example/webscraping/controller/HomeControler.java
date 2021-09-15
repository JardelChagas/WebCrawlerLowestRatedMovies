package com.example.webscraping.controller;

import com.example.webscraping.model.Filme;
import com.example.webscraping.domain.WebScraping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeControler {
    @GetMapping("/")
    public String home(){
        return "hi";
    }
}
