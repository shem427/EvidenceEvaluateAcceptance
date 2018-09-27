package com.suyuan.poa.webapp.code.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CodeTypeBean {
    private int codeTypeId;
    private String codeTypeName;
}
