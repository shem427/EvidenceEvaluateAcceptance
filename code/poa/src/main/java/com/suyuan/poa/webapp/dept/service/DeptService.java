package com.suyuan.poa.webapp.dept.service;

import com.suyuan.poa.webapp.dept.bean.DeptBean;
import com.suyuan.poa.webapp.dept.dao.DeptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeptService {
    @Autowired
    private DeptDao deptDao;

    /**
     * get dept's sub dept.
     * @param deptId dept id.
     * @return sub depts array list.
     */
    public List<DeptBean> getSubDept(int deptId) {
        return deptDao.getDeptByParentId(deptId);
    }

    @Transactional
    public int add(DeptBean dept) {
        return deptDao.add(dept);
    }

    @Transactional
    public int edit(DeptBean dept) {
        return deptDao.edit(dept);
    }

    @Transactional
    public int delete(int deptId) {
        if (deptId <= 0) {
            return 0;
        }
        return deptDao.delete(deptId);
    }

    public boolean hasChildren(int deptId) {
        List<DeptBean> subList = deptDao.getDeptByParentId(deptId);
        return subList != null && !subList.isEmpty();
    }
}
