package com.test.wemakeprice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlParserController {

    @GetMapping
    public String urlParser() {
        return "url_parser";
    }
}
