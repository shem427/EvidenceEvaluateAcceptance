package com.suyuan.poa.webapp.setting.controller;

import com.suyuan.poa.webapp.common.CommonBean;
import com.suyuan.poa.webapp.common.MessageService;
import com.suyuan.poa.webapp.common.PoaConstant;
import com.suyuan.poa.webapp.common.PoaUtil;
import com.suyuan.poa.webapp.setting.bean.ChangePassword;
import com.suyuan.poa.webapp.setting.service.SettingService;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    /**
     * LOG
     */
    private static Logger LOG = LoggerFactory.getLogger(SettingController.class);

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
        LOG.debug(messageService.getLogEntry("poa.setting.changePassword"));

        CommonBean bean = new CommonBean();
        UserBean user = PoaUtil.getUserFromSecurity();
        try {
            String policeNo = user.getPoliceNumber();
            boolean valid = settingService.checkOldPassword(policeNo, cpBean.getOldPassword());
            if (!valid) {
                bean.setStatus(CommonBean.Status.WARNING);
                bean.setMessage(messageService.getMessage("poa.change.password.invalid.old"));
                return bean;
            }
            String password = settingService.changePassword(policeNo, cpBean.getNewPassword());
            user.setPassword(password);
        } catch (Exception e) {
            String message = messageService.getMessage(PoaConstant.LOG_ERROR);
            LOG.error(message, e);
            bean.setStatus(CommonBean.Status.ERROR);
            bean.setMessage(message);
        }

        LOG.debug(messageService.getLogExit("poa.setting.changePassword"));
        return bean;
    }

    @PostMapping(value = "/updateProfile")
    @ResponseBody
    public CommonBean updateProfile(UserBean profile) {
        LOG.debug(messageService.getLogEntry("poa.setting.updateProfile"));

        CommonBean bean = new CommonBean();
        try {
            settingService.updateProfile(profile);
            UserBean user = PoaUtil.getUserFromSecurity();
            user.setName(profile.getName());
            user.setPoliceNumber(profile.getPoliceNumber());
            user.setPhoneNumber(profile.getPhoneNumber());
        } catch (Exception e) {
            String message = messageService.getMessage(PoaConstant.LOG_ERROR);
            LOG.error(message, e);
            bean.setStatus(CommonBean.Status.ERROR);
            bean.setMessage(message);
        }

        LOG.debug(messageService.getLogExit("poa.setting.updateProfile"));
        return bean;
    }
}
