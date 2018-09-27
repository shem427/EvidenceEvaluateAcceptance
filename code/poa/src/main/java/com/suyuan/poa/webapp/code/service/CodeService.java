package com.suyuan.poa.webapp.code.service;

import com.suyuan.poa.webapp.code.bean.CodeBean;
import com.suyuan.poa.webapp.code.bean.CodeTypeBean;
import com.suyuan.poa.webapp.code.dao.CodeDao;
import com.suyuan.poa.webapp.common.SearchParam;
import com.suyuan.poa.webapp.common.TableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CodeService {
    @Autowired
    private CodeDao codeDao;

    /**
     * 检索模块信息。
     * @param searchParam 共通检索条件
     * @param codeTypeId 模块类型ID
     * @param codeNameLike 模块名模糊匹配
     * @return 检索结果
     */
    public TableData<CodeBean> searchCode(SearchParam searchParam, int codeTypeId, String codeNameLike) {
        int total = codeDao.count(searchParam, codeTypeId, codeNameLike);
        List<CodeBean> rows = codeDao.search(searchParam, codeTypeId, codeNameLike);
        TableData<CodeBean> tableData = new TableData<>();
        tableData.setTotal(total);
        tableData.setRows(rows);

        return tableData;
    }

    /**
     * 删除模块信息。
     * @param codeIdList 模块ID List
     * @return 删除的件数
     */
    @Transactional
    public int deleteCode(List<Integer> codeIdList) {
        int count = 0;
        for (int codeId : codeIdList) {
            count += codeDao.deleteCode(codeId);
        }
        return count;
    }

    /**
     * 获取所有的模块类型。
     * @return 模块类型
     */
    public List<CodeTypeBean> getAllTypes() {
        return codeDao.getAllCodeType();
    }

    @Transactional
    public int saveCode(CodeBean code) {
        int codeTypeId = code.getCodeTypeId();
        if (codeTypeId <= 0) {
            codeTypeId = codeDao.getNextCodeTypeId();
            code.setCodeTypeId(codeTypeId);
        }
        if (code.getCodeId() > 0) {
            // 编辑
            return codeDao.saveCode(code);
        } else {
            // 添加
            return codeDao.createDao(code);
        }
    }
}
