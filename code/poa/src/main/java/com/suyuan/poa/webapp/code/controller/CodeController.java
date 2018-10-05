package com.suyuan.poa.webapp.code.controller;

import com.suyuan.poa.webapp.code.bean.CodeBean;
import com.suyuan.poa.webapp.code.bean.CodeTypeBean;
import com.suyuan.poa.webapp.code.service.CodeService;
import com.suyuan.poa.webapp.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模块管理Controller类
 */
@Controller
@RequestMapping(value = "/code")
public class CodeController {
    /**
     * LOG
     */
    private static final Logger LOG = LoggerFactory.getLogger(CodeController.class);

    @Autowired
    private CodeService codeService;

    @Autowired
    private MessageService messageService;
    /**
     * 显示模块管理初始页面。
     * @return 模块管理初始页面
     */
    @GetMapping(value = "/index")
    public String page() {
        return "code/page";
    }

    @GetMapping(value = "/addPage")
    public ModelAndView modalAddPage() {
        List<CodeTypeBean> codeTypes = codeService.getAllTypes();
        Map<String, Object> model = new HashMap<>();
        model.put("codeTypes", codeTypes);
        return new ModelAndView("code/modalPage", model);
    }

    @GetMapping(value = "/editPage")
    public ModelAndView modalEditPage(int codeId, int codeTypeId, String codeName) {
        List<CodeTypeBean> codeTypes = codeService.getAllTypes();
        Map<String, Object> model = new HashMap<>();
        model.put("codeTypes", codeTypes);
        model.put("codeId", codeId);
        model.put("codeTypeId", codeTypeId);
        model.put("codeName", codeName);

        return new ModelAndView("code/modalPage", model);
    }

    @GetMapping(value = "/codeList")
    @ResponseBody
    public TableData<CodeBean> searchCode(SearchParam searchParam, String codeTypeId, String codeNameLike) {
        LOG.debug(messageService.getLogEntry("poa.code.search"));

        TableData<CodeBean> tableData = null;
        int typeId = -1;
        try {
            if (!StringUtils.isEmpty(codeTypeId)) {
                typeId = Integer.parseInt(codeTypeId);
            }
            tableData = codeService.searchCode(searchParam, typeId, codeNameLike);
        } catch (Exception e) {
            tableData = new TableData<CodeBean>();
            String message = messageService.getMessage(PoaConstant.LOG_ERROR);
            LOG.error(message, e);
            tableData.setStatus(CommonBean.Status.ERROR);
            tableData.setMessage(message);
        }

        LOG.debug(messageService.getLogExit("poa.code.search"));
        return tableData;
    }

    @GetMapping(value = "/getAllTypes")
    @ResponseBody
    public List<CodeTypeBean> getAllTypes() {
        LOG.debug(messageService.getLogEntry("poa.code.getAllType"));

        List<CodeTypeBean> typeList = codeService.getAllTypes();

        LOG.debug(messageService.getLogExit("poa.code.getAllType"));

        return typeList;
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    public CommonBean deleteCode(@RequestBody List<Integer> codeIdList) {
        LOG.debug(messageService.getLogEntry("poa.code.delete"));

        CommonBean bean = new CommonBean();
        try {
            codeService.deleteCode(codeIdList);
        } catch (Exception e) {
            String message = messageService.getMessage(PoaConstant.LOG_ERROR);
            LOG.error(message, e);
            bean.setStatus(CommonBean.Status.ERROR);
            bean.setMessage(message);
        }

        LOG.debug(messageService.getLogExit("poa.code.delete"));
        return bean;
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public CommonBean saveCode(CodeBean codeBean) {
        LOG.debug(messageService.getLogEntry("poa.code.save"));

        CommonBean bean = new CommonBean();
        try {
            codeService.saveCode(codeBean);
        } catch (Exception e) {
            String message = messageService.getMessage(PoaConstant.LOG_ERROR);
            LOG.error(message, e);
            bean.setStatus(CommonBean.Status.ERROR);
            bean.setMessage(message);
        }

        LOG.debug(messageService.getLogExit("poa.code.save"));
        return bean;
    }
}
