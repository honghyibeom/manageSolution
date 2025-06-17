package com.example.managesolution.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
//@RequestMapping("")
public class TestController {

    @GetMapping("/main")
    public String test() {
        return "main";
    }
}
