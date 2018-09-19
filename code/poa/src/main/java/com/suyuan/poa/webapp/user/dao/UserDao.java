package com.suyuan.poa.webapp.user.dao;

import com.suyuan.poa.webapp.common.CommonDao;
import com.suyuan.poa.webapp.common.DBConstant;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDao extends CommonDao<UserBean> {
    @Override
    protected UserBean convertBean(ResultSet rs) throws SQLException {
        UserBean bean = new UserBean();
        bean.setUserId(rs.getInt(DBConstant.USER_ID));
        bean.setName(rs.getString(DBConstant.USER_NAME));
        bean.setPoliceNumber(rs.getString(DBConstant.POLICE_NUMBER));
        bean.setPhoneNumber(rs.getString(DBConstant.PHONE_NUMBER));
        bean.setUserRoles(rs.getString(DBConstant.USER_ROLE));
        bean.setPassword(rs.getString(DBConstant.PASSWORD));

        return bean;
    }

    @Override
    protected String getTableName() {
        return DBConstant.USER_TABLE;
    }

    @Override
    protected String idName() {
        return DBConstant.USER_ID;
    }

    /**
     * 根据警号查询用户
     * @param policeNumber 警号
     * @return 用户对象
     */
    public UserBean getUserByPoliceNumber(String policeNumber) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT ");
        sbSQL.append("U.").append(DBConstant.USER_ID).append(",");
        sbSQL.append("U.").append(DBConstant.USER_NAME).append(",");
        sbSQL.append("U.").append(DBConstant.POLICE_NUMBER).append(",");
        sbSQL.append("U.").append(DBConstant.PHONE_NUMBER).append(",");
        sbSQL.append("U.").append(DBConstant.USER_ROLE).append(",");
        sbSQL.append("U.").append(DBConstant.PASSWORD).append(",");
        sbSQL.append("U.").append(DBConstant.DEPT_ID).append(",");
        sbSQL.append("D.").append(DBConstant.DEPT_NAME);

        sbSQL.append(" FROM ");
        sbSQL.append(DBConstant.USER_TABLE).append(" U,").append(DBConstant.DEPT_TABLE).append(" D");
        sbSQL.append(" WHERE ");
        sbSQL.append("U.").append(DBConstant.DEPT_ID).append("=").append("D.").append(DBConstant.DEPT_ID);
        sbSQL.append(" AND U.").append(DBConstant.POLICE_NUMBER).append("=?");

        UserBean bean = jdbcTemplate.query(sbSQL.toString(),
                new String[] {policeNumber}, rs -> {
                    UserBean u = null;
                    while(rs.next()) {
                        u = convertBeanWithDept(rs);
                    }
                    return u;
                });
        return bean;
    }

    public UserBean convertBeanWithDept(ResultSet rs) throws SQLException {
        UserBean bean = convertBean(rs);
        bean.setDeptId(rs.getInt(DBConstant.DEPT_ID));
        bean.setDeptName(rs.getString(DBConstant.DEPT_NAME));

        return bean;
    }
}
