package com.suyuan.poa.webapp.dept.bean;

import com.suyuan.poa.webapp.common.CommonTreeBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 组织Bean类。
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptBean extends CommonTreeBean {
    /** 组织备注 */
    private String deptRemark;
    /** 组织管理者ID List */
    private List<Integer> managerIdList;
}
