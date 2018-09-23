package com.suyuan.poa.webapp.dept.bean;

import com.suyuan.poa.webapp.common.CommonTreeBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptBean extends CommonTreeBean {
    private String deptRemark;
}
