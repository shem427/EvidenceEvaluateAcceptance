package com.suyuan.poa.webapp.dept.service;

import com.suyuan.poa.webapp.dept.bean.DeptBean;
import com.suyuan.poa.webapp.dept.dao.DeptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {
    @Autowired
    private DeptDao deptDao;

    public List<DeptBean> getAllDept() {
        return deptDao.getAll();
    }
}
