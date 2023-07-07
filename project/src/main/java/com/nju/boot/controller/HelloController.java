package com.nju.boot.controller;

import com.nju.boot.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {
    @Resource
    private HelloService helloService;


    @GetMapping("/hello")
    public String hello(){
        return "Hello, Spring Boot!";
    }

}
