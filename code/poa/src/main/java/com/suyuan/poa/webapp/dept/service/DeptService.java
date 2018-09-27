package com.suyuan.poa.webapp.dept.service;

import com.suyuan.poa.webapp.dept.bean.DeptBean;
import com.suyuan.poa.webapp.dept.dao.DeptDao;
import com.suyuan.poa.webapp.user.bean.UserBean;
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
        // insert dept and return dept id.
        int deptId = deptDao.add(dept);
        // save dept_manager.
        saveDeptManagers(deptId, dept.getManagerIdList());

        return deptId;
    }

    @Transactional
    public int edit(DeptBean dept) {
        // save dept.
        int ret = deptDao.edit(dept);
        // save dept_manager.
        saveDeptManagers(dept.getId(), dept.getManagerIdList());

        return ret;
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

    public List<UserBean> getDeptManagers(int deptId) {
        if (deptId <= 0) {
            return null;
        }
        return deptDao.getManagers(deptId);
    }

    private void saveDeptManagers(int deptId, List<Integer> userIdList) {
        deptDao.deleteManagers(deptId);
        deptDao.saveManagers(deptId, userIdList);
    }
}
