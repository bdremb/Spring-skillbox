package com.example.spring.demojooq.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SimpleController {

    @GetMapping("/example")
    public String index(Model model) {
        model.addAttribute("userName", "Petrov Petr");

        List<String> departments = List.of("South", "West", "Center");
        model.addAttribute("departments", departments);

        User user = new User("Petr", "petr@mail.com");
        model.addAttribute("user", user);
        return "example/index";
    }

    @PostMapping("/example/save")
    public String saveUser(@ModelAttribute User user) {
        System.out.println("Saving user " + user);
        return "redirect:/example";
    }

    @GetMapping("/example/footer")
    public String getFooter() {
        return "example/fragments/footer-page :: footer";
    }

    @GetMapping("example/header")
    public String getHeader() {
        return "example/fragments/header-page :: header";
    }

    @Data
    @AllArgsConstructor
    class User {
        private String userName;
        private String userEmail;
    }

}
