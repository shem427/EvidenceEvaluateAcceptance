package com.suyuan.poa.webapp.user.controller;

import com.suyuan.poa.webapp.common.CommonBean;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static List<UserBean> userList = null;

    @GetMapping(value = "/index")
    public String page() {
        return "user/page";
    }
}
