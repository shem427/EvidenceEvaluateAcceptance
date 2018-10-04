package com.suyuan.poa.webapp.dept.controller;

import com.suyuan.poa.webapp.common.CommonBean;
import com.suyuan.poa.webapp.common.MessageService;
import com.suyuan.poa.webapp.dept.bean.DeptBean;
import com.suyuan.poa.webapp.dept.service.DeptService;
import com.suyuan.poa.webapp.user.bean.UserBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织Controller类。
 */
@Controller
@RequestMapping(value = "/dept")
public class DeptController {
    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(DeptController.class);

    /** 组织service对象 */
    @Autowired
    private DeptService deptService;

    /** message service对象 */
    @Autowired
    private MessageService messageService;

    /**
     * 显示组织管理初始页面。
     * @return 组织管理初始页面
     */
    @GetMapping(value = "/index")
    public String page() {
        return "dept/page";
    }

    /**
     * 获取下位组织信息。
     * @param id 组织ID
     * @return 下位组织信息
     */
    @GetMapping(value = "/subDept")
    @ResponseBody
    public List<DeptBean> getSubDept(Integer id) {
        LOG.debug(messageService.getLogEntry("poa.dept.sub"));

        int deptId = 0;
        if (id != null) {
            deptId = id.intValue();
        }
        List<DeptBean> subList = deptService.getSubDept(deptId);

        LOG.debug(messageService.getLogExit("poa.dept.sub"));
        return subList;
    }

    /**
     * 显示组织添加模态框页面。
     * @param parentId 父组织ID
     * @param parentName 父组织名
     * @return 组织添加模态框页面
     */
    @GetMapping(value = "/addPage")
    public ModelAndView getAddPage(String parentId, String parentName) {
        Map<String, Object> model = new HashMap<>();
        model.put("parentId", parentId);
        model.put("parentName", parentName);

        return new ModelAndView("dept/modalPage", model);
    }

    /**
     * 显示组织编辑模态框页面。
     * @param parentId 父组织ID
     * @param parentName  父组织名
     * @param deptId 组织ID
     * @param deptName 组织名
     * @param deptRemark 组织备注
     * @return 组织编辑模态框页面
     */
    @GetMapping(value = "/editPage")
    public ModelAndView getEditPage(int parentId,
                                    String parentName,
                                    int deptId,
                                    String deptName,
                                    String deptRemark) {
        Map<String, Object> model = new HashMap<>();
        List<UserBean> managers = deptService.getDeptManagers(deptId);

        model.put("parentId", parentId);
        model.put("parentName", parentName);
        model.put("deptId", deptId);
        model.put("deptName", deptName);
        model.put("deptRemark", deptRemark);
        model.put("managers", managers);

        return new ModelAndView("dept/modalPage", model);
    }

    /**
     * 组织添加/编辑时的保存。
     * @param dept 页面中组织信息
     * @return 保存成功/失败
     */
    @PostMapping(value = "/saveDept")
    @ResponseBody
    public CommonBean saveDept(DeptBean dept) {
        LOG.debug(messageService.getLogEntry("poa.dept.save"));

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
            String message = messageService.getMessage("poa.server.error");
            LOG.error(message, e);
            bean.setStatus(CommonBean.Status.ERROR);
            bean.setMessage(message);
        }

        LOG.debug(messageService.getLogExit("poa.dept.save"));
        return bean;
    }

    /**
     * 删除组织信息。
     * @param deptId 组织ID
     * @return 删除成功/失败
     */
    @PostMapping(value = "deleteDept")
    @ResponseBody
    public CommonBean deleteDept(int deptId) {
        LOG.debug(messageService.getLogEntry("poa.dept.delete"));

        CommonBean retBean = new CommonBean();
        try {
            boolean hasChildren = deptService.hasChildren(deptId);
            if (hasChildren) {
                // 有下位组织，不能删除
                String message = messageService.getMessage("poa.delete.dept.hasChildren");
                retBean.setStatus(CommonBean.Status.WARNING);
                retBean.setMessage(message);
            } else {
                deptService.delete(deptId);
            }
        } catch (Exception e) {
            String message = messageService.getMessage("poa.server.error");
            LOG.error(message, e);
            retBean.setStatus(CommonBean.Status.ERROR);
            retBean.setMessage(message);
        }

        LOG.debug(messageService.getLogExit("poa.dept.delete"));
        return retBean;
    }

    /**
     * 获取组织的管理者。
     * @param deptId 组织ID
     * @return 组织管理者
     */
    @GetMapping(value = "getManagers")
    @ResponseBody
    public List<UserBean> getDeptManagers(int deptId) {
        LOG.debug(messageService.getLogEntry("poa.dept.getManagers"));

        List<UserBean> managerList = deptService.getDeptManagers(deptId);

        LOG.debug(messageService.getLogExit("poa.dept.getManagers"));
        return managerList;
    }
}
