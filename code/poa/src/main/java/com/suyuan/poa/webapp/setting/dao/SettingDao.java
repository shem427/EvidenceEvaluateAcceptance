package com.suyuan.poa.webapp.setting.dao;

import com.suyuan.poa.webapp.common.CommonDao;
import com.suyuan.poa.webapp.common.DBConstant;
import com.suyuan.poa.webapp.user.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SettingDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int changePassword(String policeNo, String cryptedPassword) {
        String sql = "UPDATE `USER` SET `USER_PASSWORD`=? where `POLICE_NUMBER`=?";
        return jdbcTemplate.update(sql, cryptedPassword, policeNo);
    }

    public String getOldPassword(String policeNo) {
        String sql = "SELECT `USER_PASSWORD` FROM `USER` WHERE `POLICE_NUMBER`=?";
        String password = jdbcTemplate.query(sql, new String[] { policeNo }, rs -> {
            String pwd = null;
            while(rs.next()) {
                pwd = rs.getString(DBConstant.PASSWORD);
            }
            return pwd;
        });

        return password;
    }

    public int updateProfile(UserBean profile) {
        String sql = "UPDATE `USER` SET `POLICE_NUMBER`=?,`USER_NAME`=?,`PHONE_NUMBER`=? WHERE USER_ID=?";
        return jdbcTemplate.update(sql,
                profile.getPoliceNumber(),
                profile.getName(),
                profile.getPhoneNumber(),
                profile.getUserId());
    }
}
