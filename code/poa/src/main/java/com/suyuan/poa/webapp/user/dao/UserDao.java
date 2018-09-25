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

    /**
     * 从ResultSet中抽取Bean对象-bean对象连带组织信息。
     * @param rs ResultSet对象
     * @return Bean对象
     * @throws SQLException SQL例外
     */
    public UserBean convertBeanWithDept(ResultSet rs) throws SQLException {
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
        // SELECT USER_ID,USER_NAME,POLICE_NUMBER,PHONE_NUMBER,USER_ROLE,USER_PASSWORD FROM USER WHERE POLICE_NUMBER LIKE ? AND USER_NAME LIKE ?

        // parameter
        List<String> argList = new ArrayList<>();
        // sql文
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT ").append(DBConstant.USER_ID).append(",");
        sbSQL.append(DBConstant.USER_NAME).append(",");
        sbSQL.append(DBConstant.POLICE_NUMBER).append(",");
        sbSQL.append(DBConstant.PHONE_NUMBER).append(",");
        sbSQL.append(DBConstant.USER_ROLE).append(",");
        sbSQL.append(DBConstant.PASSWORD).append(" FROM ");
        sbSQL.append(DBConstant.USER_TABLE);

        String sqlWhere = getWhereForSearch(policeNoLike, nameLike, argList);
        if (sqlWhere.length() > 0) {
            sbSQL.append(" WHERE ").append(sqlWhere);
        }
        sbSQL.append(param.toSQL());

        return jdbcTemplate.query(sbSQL.toString(), argList.toArray(new String[0]), getAllExtractor());
    }

    /**
     * 根据制定条件检索符合条件的人员件数。
     * @param param 共通检索条件
     * @param policeNoLike 警号模糊条件
     * @param nameLike 姓名模糊条件
     * @return 符合条件人员件数
     */
    public int count(SearchParam param, String policeNoLike, String nameLike) {
        // parameter
        List<String> argList = new ArrayList<>();

        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT COUNT(1) AS TOTAL FROM ").append(DBConstant.USER_TABLE);
        String sqlWhere = getWhereForSearch(policeNoLike, nameLike, argList);
        if (sqlWhere.length() > 0) {
            sbSQL.append(" WHERE ").append(sqlWhere);
        }

        return jdbcTemplate.query(sbSQL.toString(), argList.toArray(new String[0]), rs -> {
            int total = 0;
            while(rs.next()) {
                total = rs.getInt("TOTAL");
            }
            return total;
        });
    }

    private String getWhereForSearch(String policeNoLike, String nameLike, List<String> argList) {
        StringBuilder sbSqlWhere = new StringBuilder();
        if (!StringUtils.isEmpty(policeNoLike)) {
            argList.add("%" + policeNoLike + "%");
            sbSqlWhere.append(DBConstant.POLICE_NUMBER).append(" LIKE ?");
        }
        if (!StringUtils.isEmpty(nameLike)) {
            argList.add("%" + nameLike + "%");
            if (sbSqlWhere.length() > 0) {
                sbSqlWhere.append(" AND ");
            }
            sbSqlWhere.append(DBConstant.USER_NAME).append(" LIKE ? ");
        }
        return sbSqlWhere.toString();
    }
}
