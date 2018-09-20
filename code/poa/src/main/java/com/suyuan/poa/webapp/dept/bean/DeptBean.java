package com.suyuan.poa.webapp.dept.bean;

import com.suyuan.poa.webapp.common.CommonBean;
import com.suyuan.poa.webapp.common.CommonTreeBean;
import com.suyuan.poa.webapp.user.bean.UserBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptBean extends CommonTreeBean {
    private String deptName;
    private String deptRemark;

    private List<DeptBean> children;
}
