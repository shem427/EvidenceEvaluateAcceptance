package com.suyuan.poa.webapp.dept.controller;

import com.suyuan.poa.webapp.common.CommonBean;
import com.suyuan.poa.webapp.common.MessageService;
import com.suyuan.poa.webapp.dept.bean.DeptBean;
import com.suyuan.poa.webapp.dept.service.DeptService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dept")
public class DeptController {
    private static final Log LOG = LogFactory.getLog(DeptController.class);

    @Autowired
    private DeptService deptService;

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/index")
    public String page() {
        return "dept/page";
    }

    @GetMapping(value = "/subDept")
    @ResponseBody
    public List<DeptBean> getSubDept(Integer id) {
        int deptId = 0;
        if (id != null) {
            deptId = id.intValue();
        }
        return deptService.getSubDept(deptId);
    }

    @GetMapping(value = "/addPage")
    public ModelAndView getAddPage(String parentId, String parentName) {
        Map<String, Object> model = new HashMap<>();
        model.put("parentId", parentId);
        model.put("parentName", parentName);

        return new ModelAndView("dept/modalPage", model);
    }

    @GetMapping(value = "/editPage")
    public ModelAndView getEditPage(String parentId,
                                    String parentName,
                                    String deptId,
                                    String deptName,
                                    String deptRemark) {
        Map<String, Object> model = new HashMap<>();
        model.put("parentId", parentId);
        model.put("parentName", parentName);
        model.put("deptId", deptId);
        model.put("deptName", deptName);
        model.put("deptRemark", deptRemark);

        return new ModelAndView("dept/modalPage", model);
    }

    @PostMapping(value = "/saveDept")
    @ResponseBody
    public CommonBean saveDept(DeptBean dept) {
        CommonBean bean = new CommonBean();
        try {
            int deptId = dept.getId();
            if (deptId > 0) {
                // edit
                deptService.edit(dept);
            } else {
                // add
                deptService.add(dept);
            }
        } catch (Exception e) {
            String message = messageService.getMessage("poa.save.dept.error");
            LOG.error(message, e);
            bean.setStatus(CommonBean.Status.ERROR);
            bean.setMessage(message);
        }
        return bean;
    }

    @PostMapping(value = "deleteDept")
    @ResponseBody
    public CommonBean deleteDept(int deptId) {
        CommonBean retBean = new CommonBean();
        try {
            boolean hasChildren = deptService.hasChildren(deptId);
            if (hasChildren) {
                String message = messageService.getMessage("poa.delete.dept.hasChildren");
                retBean.setStatus(CommonBean.Status.WARNING);
                retBean.setMessage(message);
                return retBean;
            } else {
                deptService.delete(deptId);
                return retBean;
            }
        } catch (Exception e) {
            String message = messageService.getMessage("poa.delete.dept.error");
            LOG.error(message, e);
            retBean.setStatus(CommonBean.Status.ERROR);
            retBean.setMessage(message);
            return retBean;
        }
    }
}
