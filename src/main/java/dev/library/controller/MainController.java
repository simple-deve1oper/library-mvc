package dev.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    /**
     * Получение главной страницы
     */
    @GetMapping
    public String index() {
        return "main/index";
    }
}
