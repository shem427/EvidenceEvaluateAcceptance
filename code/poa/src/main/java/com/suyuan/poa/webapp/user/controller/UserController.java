package com.suyuan.poa.webapp.user.controller;

import com.suyuan.poa.webapp.common.CommonBean;
import com.suyuan.poa.webapp.common.MessageService;
import com.suyuan.poa.webapp.common.SearchParam;
import com.suyuan.poa.webapp.common.TableData;
import com.suyuan.poa.webapp.user.bean.UserBean;
import com.suyuan.poa.webapp.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    /**
     * LOG
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping(value = "/index")
    public String page() {
        return "user/page";
    }

    @GetMapping(value = "/modalUsers")
    public String modalUserList() {
        return "user/modalUsersPage";
    }

    /**
     * 根据制定条件检索符合条件的人员。
     * @param param 共通检索条件
     * @param policeNoLike 警号模糊条件
     * @param nameLike 姓名模糊条件
     * @return 符合条件人员
     */
    @GetMapping(value = "/userList")
    @ResponseBody
    public TableData<UserBean> searchUser(SearchParam param, String policeNoLike, String nameLike) {
        LOG.debug(messageService.getLogEntry("poa.user.search"));

        TableData<UserBean> tableData = new TableData<>();
        try {
            tableData = userService.searchUser(param, policeNoLike, nameLike);
        } catch(Exception e) {
            String message = messageService.getMessage("poa.server.error");
            LOG.error(message, e);
            tableData.setStatus(CommonBean.Status.ERROR);
            tableData.setMessage(message);
        }
        LOG.debug(messageService.getLogExit("poa.user.search"));

        return tableData;
    }

    /**
     * 显示人员添加模态框页面。
     * @return 人员添加模态框页面
     */
    @GetMapping(value = "/addPage")
    public String getAddPage() {
        return "user/modalPage";
    }

    /**
     * 显示人员编辑模态框页面。
     * @param userId 人员ID
     * @param policeNumber 警号
     * @param name 人员名
     * @param phoneNumber 电话号码
     * @param deptId 组织ID
     * @param deptName 组织名
     * @return 人员编辑模态框页面
     */
    @GetMapping(value = "/editPage")
    public ModelAndView getEditPage(int userId,
                                    String policeNumber,
                                    String name,
                                    String phoneNumber,
                                    int deptId,
                                    String deptName) {
        Map<String, Object> model = new HashMap<>();

        model.put("userId", userId);
        model.put("policeNumber", policeNumber);
        model.put("phoneNumber", phoneNumber);
        model.put("name", name);
        model.put("deptId", deptId);
        model.put("deptName", deptName);

        return new ModelAndView("user/modalPage", model);
    }

    @PostMapping(value = "/save")
    public CommonBean saveUser(UserBean user) {
        LOG.debug(messageService.getLogEntry("poa.user.save"));

        CommonBean bean = new CommonBean();
        try {
            int userId = user.getUserId();
            if (userId > 0) {
                // edit
                userService.editUser(user);
            } else {
                // add
                userService.addUser(user);
            }
        } catch (Exception e) {
            String message = messageService.getMessage("poa.server.error");
            LOG.error(message, e);
            bean.setStatus(CommonBean.Status.ERROR);
            bean.setMessage(message);
        }

        LOG.debug(messageService.getLogExit("poa.user.save"));
        return bean;
    }
}
