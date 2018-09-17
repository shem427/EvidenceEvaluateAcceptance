package com.suyuan.poa.webapp.dept.bean;

import com.suyuan.poa.webapp.user.bean.UserBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptBean {
    private int deptId;
    private String deptName;
    private String deptRemark;
    private List<UserBean> adminList;
}
