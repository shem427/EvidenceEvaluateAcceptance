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
    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTableName() {
        return DBConstant.DEPT_TABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String idName() {
        return DBConstant.DEPT_ID;
    }

    /**
     * 根据父组织ID，获取下位组织信息。
     * @param pId 父组织ID
     * @return 下位组织信息
     */
    public List<DeptBean> getDeptByParentId(int pId) {
        String sql = "SELECT D.`DEPT_ID`,D.`DEPT_NAME`,D.`DEPT_REMARK`,D.`PARENT_ID` FROM `DEPT` D WHERE ";
        if (pId <= 0) {
            sql += "D.`PARENT_ID`=? OR D.`PARENT_ID` IS NULL";
        } else {
            sql += "D.`PARENT_ID`=?";
        }
        List<DeptBean> subList = jdbcTemplate.query(sql, new Integer[] {pId},
                rs -> {
                    List<DeptBean> list = new ArrayList<>();
                    while(rs.next()) {
                        list.add(convertBean(rs));
                    }
                    return list;
                });
        return subList;
    }

    /**
     * 添加组织信息。
     * @param dept 组织信息。
     * @return 添加的组织ID
     */
    public int add(DeptBean dept) {
        String sql = "INSERT INTO DEPT (`DEPT_NAME`, `DEPT_REMARK`, `PARENT_ID`) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, dept.getName(), dept.getDeptRemark(), dept.getPId());
        return getLastInsertId();
    }

    /**
     * 编辑组织信息。
     * @param dept 组织信息
     * @return 编辑的组织件数
     */
    public int edit(DeptBean dept) {
        String sql = "UPDATE DEPT SET DEPT_NAME=?, DEPT_REMARK=? WHERE DEPT_ID=?";
        return jdbcTemplate.update(sql, dept.getName(), dept.getDeptRemark(), dept.getId());
    }

    /**
     * 删除组织信息。
     * @param deptId 组织ID
     * @return 删除的组织件数
     */
    public int delete(int deptId) {
        String sql = "DELETE FROM DEPT WHERE DEPT_ID=?";
        return jdbcTemplate.update(sql, deptId);
    }

    /**
     * 获取组织的管理者。
     * @param deptId 组织ID
     * @return 组织管理者
     */
    public List<UserBean> getManagers(int deptId) {
        String sql = "SELECT M.DEPT_ID, D.DEPT_NAME, M.USER_ID, U.USER_NAME, U.POLICE_NUMBER FROM DEPT_MANAGER M, USER U, DEPT D"
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
        user.setPoliceNumber(rs.getString("POLICE_NUMBER"));

        return user;
    }

    /**
     * 删除组织全部管理者
     * @param deptId 组织ID
     */
    public void deleteManagers(int deptId) {
        String sql = "DELETE FROM DEPT_MANAGER WHERE DEPT_ID=?";
        jdbcTemplate.update(sql, deptId);
    }

    /**
     * 保存组织管理者
     * @param deptId 组织ID
     * @param userIdList 管理者ID List
     */
    public void saveManagers(int deptId, List<Integer> userIdList) {
        if (userIdList == null || userIdList.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO DEPT_MANAGER (`DEPT_ID`, `USER_ID`) VALUES (?,?)";
        for (int userId : userIdList) {
            jdbcTemplate.update(sql, deptId, userId);
        }
    }
}
