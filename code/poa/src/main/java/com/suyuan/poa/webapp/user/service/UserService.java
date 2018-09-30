package com.suyuan.poa.webapp.user.service;

import com.suyuan.poa.webapp.common.PoaUserService;
import com.suyuan.poa.webapp.common.SearchParam;
import com.suyuan.poa.webapp.common.TableData;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        int count = userDao.count(policeNoLike, nameLike);

        usersData.setTotal(count);
        usersData.setRows(userList);

        return usersData;
    }

    /**
     * 添加人员。
     * @param user 人员信息
     * @return 添加人员ID
     */
    @Transactional
    public int addUser(UserBean user) {
        return userDao.addUser(user);
    }

    /**
     * 删除人员（逻辑删除）。
     * @param userId 人员ID
     * @return 删除件数
     */
    @Transactional
    public int deleteUser(int userId) {
        return userDao.deleteUser(userId);
    }

    /**
     * 更新人员信息。
     * @param user 人员信息
     * @return 更新件数
     */
    @Transactional
    public int updateUser(UserBean user) {
        return userDao.updateUser(user);
    }
}
