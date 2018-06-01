package com.vitja.study.degree.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public String index() {
        return "ajax";
    }

    @GetMapping("/index")
    public String reserveIndex() {
        return "ajax";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/signUp")
    public String signUp() {
        return "signUp";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/userAccount")
    public String userAccount() {
        return "userAccount";
    }

    @GetMapping("/venues")
    public String venues() {
        return "venues";
    }

    @GetMapping("/events")
    public String events() {
        return "events";
    }

    @GetMapping("/singleVenue")
    public String singleVenue() {
        return "singleVenue";
    }

    @GetMapping("/addVenue")
    public String addVenue() {
        return "addVenue";
    }

    @GetMapping("/addEvent")
    public String addEvent() {
        return "addEvent";
    }
}