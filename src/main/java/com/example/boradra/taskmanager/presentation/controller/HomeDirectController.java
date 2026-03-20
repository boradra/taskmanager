package com.example.boradra.taskmanager.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeDirectController {
    @GetMapping("/")
    public String index() {
        return "redirect:/swagger-ui/index.html";
    }
    
}
