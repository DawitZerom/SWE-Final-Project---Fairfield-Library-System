package edu.miu.cs.cs425.fairfieldlibrarywebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "/", "", "/library", "/fairfieldlibrary" })
public class HomeController {

    @GetMapping(value = { "", "/public/home", "/home", "/secured/home" })
    public String displayHomepage() {
        return "public/index";
    }

    @GetMapping(value = { "/public/about", "/about" })
    public String displayAboutpage() {
        return "public/about";
    }

    @GetMapping(value = { "/secured/service" })
    public String services() {
        return "secured/service";
    }
}
