package com.suyuan.poa.webapp.code.dao;

import com.suyuan.poa.webapp.code.bean.CodeBean;
import com.suyuan.poa.webapp.common.CommonDao;
import com.suyuan.poa.webapp.common.DBConstant;
import com.suyuan.poa.webapp.common.SearchParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 模块管理DAO类
 */
public class CodeDao extends CommonDao<CodeBean> {
    /**
     * {@inheritDoc}
     */
    @Override
    protected CodeBean convertBean(ResultSet rs) throws SQLException {
        CodeBean bean = new CodeBean();
        bean.setCodeId(rs.getInt(DBConstant.CODE_ID));
        bean.setCodeName(rs.getString(DBConstant.CODE_NAME));
        bean.setCodeTypeId(rs.getInt(DBConstant.CODE_TYPE_ID));
        bean.setCodeTypeName(rs.getString(DBConstant.CODE_TYPE_NAME));

        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTableName() {
        return DBConstant.CODE_TABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String idName() {
        return DBConstant.CODE_ID;
    }

    /**
     * 新建模块。
     * @param bean 模块Bean
     * @return 插入的模块ID
     */
    public int add(CodeBean bean) {
        String sql = "INSERT INTO CODE (`CODE_TYPE_ID`,`CODE_TYPE_NAME`,`CODE_NAME`) VALUES (?,?,?)";
        jdbcTemplate.update(sql, bean.getCodeTypeId(), bean.getCodeTypeName(), bean.getCodeName());
        return getLastInsertId();
    }

    /**
     * 编辑模块。
     * @param bean 模块Bean
     * @return 编辑的条目数
     */
    public int edit(CodeBean bean) {
        String sql = "UPDATE CODE SET `CODE_TYPE_ID`=?, `CODE_TYPE_NAME`=?,`CODE_NAM`E=? WHERE `CODE_ID`=?";
        return jdbcTemplate.update(sql, bean.getCodeTypeId(),
                bean.getCodeTypeName(), bean.getCodeName(), bean.getCodeId());
    }

    /**
     * 根据条件检索模块。
     * @param param 检索条件
     * @param codeNameLike 模块名模糊检索
     * @return 符合条件的模块对象List
     */
    public List<CodeBean> search(SearchParam param, String codeNameLike) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT `");
        sbSQL.append(DBConstant.CODE_ID).append("`,`");
        sbSQL.append(DBConstant.CODE_NAME).append("`,`");
        sbSQL.append(DBConstant.CODE_TYPE_ID).append("`,`");
        sbSQL.append(DBConstant.CODE_TYPE_NAME).append("` FROM `");
        sbSQL.append(DBConstant.CODE_TABLE);
        sbSQL.append("` WHERE `");
        sbSQL.append(DBConstant.DEPT_NAME).append("` LIKE ? ");
        sbSQL.append(param.toSQL());

        return jdbcTemplate.query(sbSQL.toString(),
                new String[]{ "%" + codeNameLike + "%" }, getAllExtractor());
    }
}
