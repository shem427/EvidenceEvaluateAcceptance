package com.suyuan.poa.webapp.dept.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptBean {
    private int deptId;
    private String deptName;
    private String deptRemark;
}
