package com.suyuan.poa.webapp.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/code")
public class CodeController {
    @GetMapping(value = "/index")
    public String page() {
        return "code/page";
    }
}
