package com.suyuan.poa.webapp.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @GetMapping(value = "/index")
    public String page() {
        return "dashboard/page";
    }
}
