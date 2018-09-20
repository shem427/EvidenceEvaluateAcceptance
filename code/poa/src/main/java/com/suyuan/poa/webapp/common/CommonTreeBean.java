package com.suyuan.poa.webapp.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommonTreeBean extends CommonBean {
    private String id;
    private String pId;
}
