package com.tingeso.eval1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class MenuController {

    @GetMapping("/")
    public String main(){
        return("menu");
    }

}
