package com.suyuan.poa.webapp.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 树对象的父类。
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommonTreeBean extends CommonBean {
    private int id;
    private int pId;
    private String name;

    private Boolean isParent;
}
