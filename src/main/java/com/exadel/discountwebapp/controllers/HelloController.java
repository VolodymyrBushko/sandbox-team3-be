package com.exadel.discountwebapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    // comments
    @GetMapping({"", "/"})
    public String hello() {
        return "hello";
    }
}
