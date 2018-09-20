package com.suyuan.poa.webapp.dept.controller;

import com.suyuan.poa.webapp.common.PoaUtil;
import com.suyuan.poa.webapp.dept.bean.DeptBean;
import com.suyuan.poa.webapp.dept.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping(value = "/index")
    public String page() {
        return "dept/page";
    }

    @GetMapping(value = "/deptTree")
    @ResponseBody
    public DeptBean getAllDept() {
        List<DeptBean> deptList = deptService.getAllDept();
        return PoaUtil.list2Tree(deptList);
    }
}
