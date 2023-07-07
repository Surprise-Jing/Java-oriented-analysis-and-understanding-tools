package com.nju.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @GetMapping("")
    public String getMenu(){
        return "redirect:/hello";
    }
}
