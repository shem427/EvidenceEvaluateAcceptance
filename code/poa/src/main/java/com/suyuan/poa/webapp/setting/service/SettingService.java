package com.suyuan.poa.webapp.setting.service;

import com.suyuan.poa.webapp.setting.dao.SettingDao;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
    @Autowired
    private SettingDao settingDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean checkOldPassword(String policeNo, String oldPassword) {
        String old = settingDao.getOldPassword(policeNo);
        return oldPassword.equals(old) || passwordEncoder.encode(oldPassword).equals(old);
    }

    public int changePassword(String policeNo, String newPassword) {
        String encryptPassword = passwordEncoder.encode(newPassword);
        return settingDao.changePassword(policeNo, encryptPassword);
    }

    public int updateProfile(UserBean profile) {
        return settingDao.updateProfile(profile);
    }
}
