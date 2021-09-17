package com.example.webscraping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeControler {
    @GetMapping("/")
    public String home(){
        return "go to '/film'";
    }
}
