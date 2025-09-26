package dev.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

//    private static HelloService helloService;

    @GetMapping
    public String hello() {
        return "Hello Jenkins!!!!!!!";
    }
}