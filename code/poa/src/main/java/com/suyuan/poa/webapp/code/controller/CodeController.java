package com.suyuan.poa.webapp.code.controller;

import com.suyuan.poa.webapp.code.bean.CodeBean;
import com.suyuan.poa.webapp.code.bean.CodeTypeBean;
import com.suyuan.poa.webapp.code.service.CodeService;
import com.suyuan.poa.webapp.common.CommonBean;
import com.suyuan.poa.webapp.common.MessageService;
import com.suyuan.poa.webapp.common.SearchParam;
import com.suyuan.poa.webapp.common.TableData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.jws.Oneway;
import java.util.List;

/**
 * 模块管理Controller类
 */
@Controller
@RequestMapping(value = "/code")
public class CodeController {
    private static final Log LOG = LogFactory.getLog(CodeController.class);
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

    @GetMapping(value = "/codeList")
    @ResponseBody
    public TableData<CodeBean> searchCode(SearchParam searchParam, String codeTypeId, String codeNameLike) {
        TableData<CodeBean> tableData = null;
        int typeId = -1;
        try {
            if (!StringUtils.isEmpty(codeTypeId)) {
                typeId = Integer.parseInt(codeTypeId);
            }
            tableData = codeService.searchCode(searchParam, typeId, codeNameLike);
        } catch (Exception e) {
            tableData = new TableData<CodeBean>();
            String message = messageService.getMessage("poa.search.code.error");
            LOG.error(message, e);
            tableData.setStatus(CommonBean.Status.ERROR);
            tableData.setMessage(message);
        }

        return tableData;
    }

    @GetMapping(value = "/getAllTypes")
    @ResponseBody
    public List<CodeTypeBean> getAllTypes() {
        return codeService.getAllTypes();
    }

    @PostMapping(value = "/delete")
    @ResponseBody
    public CommonBean deleteCode(@RequestBody List<Integer> codeIdList) {
        CommonBean bean = new CommonBean();
        try {
            codeService.deleteCode(codeIdList);
        } catch (Exception e) {
            String message = messageService.getMessage("poa.delete.code.error");
            LOG.error(message, e);
            bean.setStatus(CommonBean.Status.ERROR);
            bean.setMessage(message);
        }

        return bean;
    }
}
