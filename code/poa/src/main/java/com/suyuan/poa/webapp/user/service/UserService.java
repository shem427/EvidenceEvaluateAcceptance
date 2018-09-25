package com.suyuan.poa.webapp.user.service;

import com.suyuan.poa.webapp.common.PoaUserService;
import com.suyuan.poa.webapp.common.SearchParam;
import com.suyuan.poa.webapp.common.TableData;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends PoaUserService {

    /**
     * 根据制定条件检索符合条件的人员。
     * @param param 共通检索条件
     * @param policeNoLike 警号模糊条件
     * @param nameLike 姓名模糊条件
     * @return 符合条件人员
     */
    public TableData<UserBean> searchUser(SearchParam param, String policeNoLike, String nameLike) {
        TableData<UserBean> usersData = new TableData<>();
        List<UserBean> userList = userDao.searchUser(param, policeNoLike, nameLike);
        int count = userDao.count(param, policeNoLike, nameLike);

        usersData.setTotal(count);
        usersData.setRows(userList);

        return usersData;
    }
}
