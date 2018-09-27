package com.suyuan.poa.webapp.code.dao;

import com.suyuan.poa.webapp.code.bean.CodeBean;
import com.suyuan.poa.webapp.code.bean.CodeTypeBean;
import com.suyuan.poa.webapp.common.CommonDao;
import com.suyuan.poa.webapp.common.DBConstant;
import com.suyuan.poa.webapp.common.SearchParam;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 模块管理DAO类
 */
@Component
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
        String sql = "INSERT INTO CODE (`CODE_TYPE_ID`,`CODE_TYPE_NAME`,`CODE_NAME`, `ACTIVE`) VALUES (?,?,?,true)";
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
     * @param codeTypeId 模块类型ID
     * @param codeNameLike 模块名模糊检索
     * @return 符合条件的模块对象List
     */
    public List<CodeBean> search(SearchParam param, int codeTypeId, String codeNameLike) {
        List<Object> argList = new ArrayList<>();
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT `");
        sbSQL.append(DBConstant.CODE_ID).append("`,`");
        sbSQL.append(DBConstant.CODE_NAME).append("`,`");
        sbSQL.append(DBConstant.CODE_TYPE_ID).append("`,`");
        sbSQL.append(DBConstant.CODE_TYPE_NAME).append("` FROM `");
        sbSQL.append(DBConstant.CODE_TABLE).append("`");
        sbSQL.append(" WHERE ACTIVE=true ");
        String sqlWhere = getWhereClause(codeTypeId, codeNameLike, argList);
        sbSQL.append(sqlWhere);

        sbSQL.append(param.toSQL());

        return jdbcTemplate.query(sbSQL.toString(),
                argList.toArray(),
                getAllExtractor());
    }

    public int count(SearchParam param, int codeTypeId, String codeNameLike) {
        // parameter
        List<Object> argList = new ArrayList<>();

        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT COUNT(1) AS TOTAL FROM ").append(DBConstant.CODE_TABLE);
        String sqlWhere = getWhereClause(codeTypeId, codeNameLike, argList);
        sbSQL.append(" WHERE ACTIVE=true ").append(sqlWhere);

        return jdbcTemplate.query(sbSQL.toString(), argList.toArray(), rs -> {
            int total = 0;
            while(rs.next()) {
                total = rs.getInt("TOTAL");
            }
            return total;
        });
    }

    private String getWhereClause(int codeTypeId, String codeNameLike, List<Object> argList) {
        StringBuilder sbWhereSQL = new StringBuilder();
        if (codeTypeId > 0) {
            sbWhereSQL.append(" AND `").append(DBConstant.CODE_TYPE_ID).append("`=?");
            argList.add(codeTypeId);
        }
        if (!StringUtils.isEmpty(codeNameLike)) {
            sbWhereSQL.append(" AND `").append(DBConstant.CODE_NAME).append("` Like ?");
            argList.add("%" + codeNameLike + "%");
        }
        return sbWhereSQL.toString();
    }

    /**
     * 获取所有的模块类型信息。
     * @return 模块类型信息
     */
    public List<CodeTypeBean> getAllCodeType() {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("SELECT DISTINCT `");
        sbSQL.append(DBConstant.CODE_TYPE_ID).append("`,`");
        sbSQL.append(DBConstant.CODE_TYPE_NAME).append("` FROM `");
        sbSQL.append(DBConstant.CODE_TABLE).append("`");

        return jdbcTemplate.query(sbSQL.toString(), rs -> {
            List<CodeTypeBean> typeBeanList = new ArrayList<>();
            while (rs.next()) {
                CodeTypeBean typeBean = new CodeTypeBean();
                typeBean.setCodeTypeId(rs.getInt(DBConstant.CODE_TYPE_ID));
                typeBean.setCodeTypeName(rs.getString(DBConstant.CODE_TYPE_NAME));
                typeBeanList.add(typeBean);
            }
            return typeBeanList;
        });
    }

    /**
     * 删除模块（逻辑删除，非物理删除）。
     * @param codeId 模块ID
     * @return 更新件数
     */
    public int deleteCode(int codeId) {
        String sql = "UPDATE " + DBConstant.CODE_TABLE + " SET "
                + DBConstant.IS_ACTIVE + "=false WHERE "
                + DBConstant.CODE_ID + "=?";
        return jdbcTemplate.update(sql, codeId);
    }

    public synchronized int getNextCodeTypeId() {
        String sql = "SELECT MAX(`"+ DBConstant.CODE_TYPE_ID + "`) + 1 AS NXT FROM "
                + DBConstant.CODE_TABLE;
        return jdbcTemplate.query(sql, rs -> {
            int maxId = 0;
            while (rs.next()) {
                maxId = rs.getInt("NXT");
            }
            return maxId;
        });
    }

    public int saveCode(CodeBean code) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("UPDATE ").append(DBConstant.CODE_TABLE);
        sbSQL.append(" SET ").append(DBConstant.CODE_NAME).append("=? WHERE ");
        sbSQL.append(DBConstant.CODE_ID).append("=?");
        return jdbcTemplate.update(sbSQL.toString(), code.getCodeName(), code.getCodeId());
    }

    public int createDao(CodeBean code) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append("INSERT INTO ").append(DBConstant.CODE_TABLE).append("(")
                .append(DBConstant.CODE_TYPE_ID).append(",")
                .append(DBConstant.CODE_TYPE_NAME).append(",")
                .append(DBConstant.CODE_NAME).append(",")
                .append(DBConstant.IS_ACTIVE)
                .append(") VALUES(?,?,?,?)");
        return jdbcTemplate.update(sbSQL.toString(), code.getCodeTypeId(),
                code.getCodeTypeName(), code.getCodeName(), true);
    }
}
