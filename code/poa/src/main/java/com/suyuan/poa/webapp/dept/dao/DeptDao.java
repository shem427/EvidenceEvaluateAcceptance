package com.suyuan.poa.webapp.dept.dao;

import com.suyuan.poa.webapp.common.CommonDao;
import com.suyuan.poa.webapp.common.DBConstant;
import com.suyuan.poa.webapp.dept.bean.DeptBean;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DeptDao extends CommonDao<DeptBean> {
    @Override
    protected DeptBean convertBean(ResultSet rs) throws SQLException {
        DeptBean bean = new DeptBean();
        bean.setId(rs.getInt(DBConstant.DEPT_ID));
        bean.setPId(rs.getInt(DBConstant.DEPT_PARENT_ID));
        bean.setDeptName(rs.getString(DBConstant.DEPT_NAME));
        bean.setDeptRemark(rs.getString(DBConstant.DEPT_REMARK));

        return bean;
    }

    @Override
    protected String getTableName() {
        return DBConstant.DEPT_TABLE;
    }

    @Override
    protected String idName() {
        return DBConstant.DEPT_ID;
    }
}
