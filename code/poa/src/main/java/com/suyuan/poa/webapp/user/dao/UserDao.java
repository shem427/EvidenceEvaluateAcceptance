package com.suyuan.poa.webapp.user.dao;

import com.suyuan.poa.webapp.common.CommonDao;
import com.suyuan.poa.webapp.common.DBConstant;
import com.suyuan.poa.webapp.common.SearchParam;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao extends CommonDao<UserBean> {
    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTableName() {
        return DBConstant.USER_TABLE;
    }

    /**
     * {@inheritDoc}
     */
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
        sbSQL.append(DBConstant.IS_ACTIVE).append("=true");
        sbSQL.append(" AND U.").append(DBConstant.DEPT_ID).append("=").append("D.").append(DBConstant.DEPT_ID);
        sbSQL.append(" AND U.").append(DBConstant.POLICE_NUMBER).append("=?");

        return jdbcTemplate.query(sbSQL.toString(),
                new String[] {policeNumber}, rs -> {
                    UserBean u = null;
                    while(rs.next()) {
                        u = convertBeanWithDept(rs);
                    }
                    return u;
                });
    }

    /**
     * 从ResultSet中抽取Bean对象-bean对象连带组织信息。
     * @param rs ResultSet对象
     * @return Bean对象
     * @throws SQLException SQL例外
     */
    private UserBean convertBeanWithDept(ResultSet rs) throws SQLException {
        UserBean bean = convertBean(rs);
        bean.setDeptId(rs.getInt(DBConstant.DEPT_ID));
        bean.setDeptName(rs.getString(DBConstant.DEPT_NAME));

        return bean;
    }

    /**
     * 根据制定条件检索符合条件的人员。
     * @param param 共通检索条件
     * @param policeNoLike 警号模糊条件
     * @param nameLike 姓名模糊条件
     * @return 符合条件人员
     */
    public List<UserBean> searchUser(SearchParam param, String policeNoLike, String nameLike) {
        // parameter
        List<String> argList = new ArrayList<>();
        // sql文
        String sql = "SELECT U.`USER_ID`,U.`USER_NAME`,U.`POLICE_NUMBER`,U.`PHONE_NUMBER`,U.`USER_ROLE`,U.`USER_PASSWORD`,U.`DEPT_ID`,D.`DEPT_NAME` FROM `USER` U, `DEPT` D";

        String sqlWhere = getWhereForSearch(policeNoLike, nameLike, argList);
        if (sqlWhere.length() > 0) {
            sql = sql + " WHERE " + sqlWhere;
        }
        sql += " AND U.`DEPT_ID`=D.`DEPT_ID`";
        sql += param.toSQL();

        return jdbcTemplate.query(sql, argList.toArray(new String[0]), rs -> {
            List<UserBean> beanList = new ArrayList<>();
            while(rs.next()) {
                UserBean bean = convertBeanWithDept(rs);
                beanList.add(bean);
            }
            return beanList;
        });
    }

    /**
     * 根据制定条件检索符合条件的人员件数。
     * @param policeNoLike 警号模糊条件
     * @param nameLike 姓名模糊条件
     * @return 符合条件人员件数
     */
    public int count(String policeNoLike, String nameLike) {
        // parameter
        List<String> argList = new ArrayList<>();
        String sql = "SELECT COUNT(1) AS TOTAL FROM `USER`";
        String sqlWhere = getWhereForSearch(policeNoLike, nameLike, argList);
        if (sqlWhere.length() > 0) {
            sql = sql + " WHERE " + sqlWhere;
        }

        return jdbcTemplate.query(sql, argList.toArray(new String[0]), rs -> {
            int total = 0;
            while(rs.next()) {
                total = rs.getInt("TOTAL");
            }
            return total;
        });
    }

    /**
     * 删除人员（逻辑删除）。
     * @param userId 人员ID
     * @return 删除件数
     */
    public int deleteUser(int userId) {
        String sql = "UPDATE `USER` SET ACTIVE=false WHERE `USER_ID`=?";

        return jdbcTemplate.update(sql, userId);
    }

    /**
     * 添加人员
     * @param user 人员信息
     * @return 添加人员的ID
     */
    public int addUser(UserBean user) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("INSERT INTO ").append(DBConstant.USER_TABLE);
        sbSQL.append("(");
        sbSQL.append(DBConstant.USER_NAME).append(",");
        sbSQL.append(DBConstant.POLICE_NUMBER).append(",");
        sbSQL.append(DBConstant.PHONE_NUMBER).append(",");
        sbSQL.append(DBConstant.USER_ROLE).append(",");
        sbSQL.append(DBConstant.PASSWORD).append(",");
        sbSQL.append(DBConstant.DEPT_ID).append(",");
        sbSQL.append(DBConstant.IS_ACTIVE);
        sbSQL.append(") VALUES (?,?,?,?,?,?,true)");

        int count = jdbcTemplate.update(sbSQL.toString(),
                user.getName(),
                user.getPoliceNumber(),
                user.getPhoneNumber(),
                user.getUserRoles(),
                user.getPassword(),
                user.getDeptId());
        if (count > 0) {
            return getLastInsertId();
        }
        return 0;
    }

    /**
     * 更新人员信息。
     * @param user 人员信息
     * @return 更新件数
     */
    public int updateUser(UserBean user) {
        String sql = "UPDATE `USER` SET "
                + "`USER_NAME`=?,`POLICE_NUMBER`=?,`PHONE_NUMBER`=?,`DEPT_ID`=?,`USER_ROLE`=?"
                + " WHERE `USER_ID`=?";
        return jdbcTemplate.update(sql,
                user.getName(),
                user.getPoliceNumber(),
                user.getPhoneNumber(),
                user.getDeptId(),
                user.getUserRoles(),
                user.getUserId());
    }

    private String getWhereForSearch(String policeNoLike, String nameLike, List<String> argList) {
        StringBuilder sbSqlWhere = new StringBuilder();
        sbSqlWhere.append(DBConstant.IS_ACTIVE).append("=true");
        if (!StringUtils.isEmpty(policeNoLike)) {
            argList.add("%" + policeNoLike + "%");
            sbSqlWhere.append(" AND ").append(DBConstant.POLICE_NUMBER).append(" LIKE ?");
        }
        if (!StringUtils.isEmpty(nameLike)) {
            argList.add("%" + nameLike + "%");
            sbSqlWhere.append(" AND ").append(DBConstant.USER_NAME).append(" LIKE ? ");
        }
        return sbSqlWhere.toString();
    }
}
