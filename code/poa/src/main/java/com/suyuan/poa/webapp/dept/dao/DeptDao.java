package com.suyuan.poa.webapp.dept.dao;

import com.suyuan.poa.webapp.common.CommonDao;
import com.suyuan.poa.webapp.common.DBConstant;
import com.suyuan.poa.webapp.dept.bean.DeptBean;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeptDao extends CommonDao<DeptBean> {
    @Override
    protected DeptBean convertBean(ResultSet rs) throws SQLException {
        DeptBean bean = new DeptBean();
        bean.setId(rs.getInt(DBConstant.DEPT_ID));
        bean.setPId(rs.getInt(DBConstant.DEPT_PARENT_ID));
        bean.setName(rs.getString(DBConstant.DEPT_NAME));
        bean.setDeptRemark(rs.getString(DBConstant.DEPT_REMARK));
        bean.setIsParent(true);

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

    public List<DeptBean> getDeptByParentId(int pId) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT ");
        sbSQL.append("D.").append(DBConstant.DEPT_ID).append(",");
        sbSQL.append("D.").append(DBConstant.DEPT_NAME).append(",");
        sbSQL.append("D.").append(DBConstant.DEPT_REMARK).append(",");
        sbSQL.append("D.").append(DBConstant.DEPT_PARENT_ID);
        sbSQL.append(" FROM ");
        sbSQL.append(DBConstant.DEPT_TABLE).append(" D");
        sbSQL.append(" WHERE ");
        if (pId <= 0) {
            sbSQL.append("D.").append(DBConstant.DEPT_PARENT_ID).append("=? OR ");
            sbSQL.append("D.").append(DBConstant.DEPT_PARENT_ID).append(" IS NULL");
        } else {
            sbSQL.append("D.").append(DBConstant.DEPT_PARENT_ID).append("=?");
        }
        List<DeptBean> subList = jdbcTemplate.query(sbSQL.toString(), new Integer[] {pId},
                rs -> {
                    List<DeptBean> list = new ArrayList<>();
                    while(rs.next()) {
                        list.add(convertBean(rs));
                    }
                    return list;
                });
        return subList;
    }

    public int add(DeptBean dept) {
        String sql = "INSERT INTO DEPT (`DEPT_NAME`, `DEPT_REMARK`, `PARENT_ID`) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, dept.getName(), dept.getDeptRemark(), dept.getPId());
        return getLastInsertId();
    }

    public int edit(DeptBean dept) {
        String sql = "UPDATE DEPT SET DEPT_NAME=?, DEPT_REMARK=? WHERE DEPT_ID=?";
        return jdbcTemplate.update(sql, dept.getName(), dept.getDeptRemark(), dept.getId());
    }

    public int delete(int deptId) {
        String sql = "DELETE FROM DEPT WHERE DEPT_ID=?";
        return jdbcTemplate.update(sql, deptId);
    }

    public List<UserBean> getManagers(int deptId) {
        String sql = "SELECT M.DEPT_ID, D.DEPT_NAME, M.USER_ID, U.USER_NAME FROM DEPT_MANAGER M, USER U, DEPT D"
                + " WHERE M.DEPT_ID=D.DEPT_ID AND M.USER_ID=U.USER_ID AND D.DEPT_ID=?";
        List<UserBean> userList = jdbcTemplate.query(sql, new Integer[] {deptId},
                rs -> {
                    List<UserBean> us = new ArrayList<>();
                    while (rs.next()) {
                        us.add(getMananger(rs));
                    }
                    return us;
                });
        return userList;
    }

    private UserBean getMananger(ResultSet rs) throws SQLException {
        UserBean user = new UserBean();
        user.setUserId(rs.getInt("USER_ID"));
        user.setName(rs.getString("USER_NAME"));

        return user;
    }

    public void deleteManagers(int deptId) {
        String sql = "DELETE FROM DEPT_MANAGER WHERE DEPT_ID=?";
        jdbcTemplate.update(sql, deptId);
    }

    public void saveManagers(int deptId, List<Integer> userIdList) {
        if (userIdList == null || userIdList.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO DEPT_MANAGER (DEPT_ID, USER_ID) VALUES (?,?)";
        for (int userId : userIdList) {
            jdbcTemplate.update(sql, deptId, userId);
        }
    }
}
