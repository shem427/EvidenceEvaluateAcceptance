package com.suyuan.poa.webapp.setting.controller;

import com.suyuan.poa.webapp.common.CommonBean;
import com.suyuan.poa.webapp.common.MessageService;
import com.suyuan.poa.webapp.setting.bean.ChangePassword;
import com.suyuan.poa.webapp.setting.service.SettingService;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/changePasswordPage")
    public String changePasswordPage() {
        return "setting/changePasswordPage";
    }

    @GetMapping(value = "/updateProfilePage")
    public ModelAndView updateProfilePage() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        UserBean user = (UserBean) auth.getPrincipal();

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        return new ModelAndView("setting/updateProfilePage", model);
    }

    @PostMapping(value = "/changePassword")
    @ResponseBody
    public CommonBean changePassword(ChangePassword cpBean) {
        CommonBean bean = new CommonBean();

        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        UserBean user = (UserBean) auth.getPrincipal();

        String policeNo = user.getPoliceNumber();
        boolean valid = settingService.checkOldPassword(policeNo, cpBean.getOldPassword());
        if (!valid) {
            bean.setStatus(CommonBean.Status.WARNING);
            bean.setMessage(messageService.getMessage("poa.change.password.invalid.old"));
            return bean;
        }
        settingService.changePassword(policeNo, cpBean.getNewPassword());

        return bean;
    }
}
