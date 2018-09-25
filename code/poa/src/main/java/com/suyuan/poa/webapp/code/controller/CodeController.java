package com.suyuan.poa.webapp.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 模块管理Controller类
 */
@Controller
@RequestMapping(value = "/code")
public class CodeController {
    /**
     * 显示模块管理初始页面。
     * @return 模块管理初始页面
     */
    @GetMapping(value = "/index")
    public String page() {
        return "code/page";
    }
}
