package com.company.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/index.html")
    public String main( ) {
        return "index.html";
    }
    @GetMapping("/err404.html")
    public String err404( ) {
        return "err404.html";
    }
}
