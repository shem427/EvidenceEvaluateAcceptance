package com.suyuan.poa.webapp.code.bean;

import com.suyuan.poa.webapp.common.CommonBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模块Class
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CodeBean extends CommonBean {
    private int codeId;
    private int codeTypeId;
    private String codeTypeName;
    private String codeName;
}
