package com.suyuan.poa.webapp.common;

import com.suyuan.poa.webapp.user.bean.UserBean;
import com.suyuan.poa.webapp.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PoaUserService implements UserDetailsService {
    /**
     * DB中ROLE的分隔符
     */
    private static final String ROLE_SEPERATOR = ",";

    @Autowired
    protected UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String policeNumber) throws UsernameNotFoundException {
        UserBean user = userDao.getUserByPoliceNumber(policeNumber);
        if (user == null) {
            throw new UsernameNotFoundException(policeNumber + " do not exist!");
        }

        user.setAuthorities(getAuthorities(user.getUserRoles()));
        return user;
    }

    /**
     * 根据用户Role设置权限。
     * @param userRoles db中存储的role
     * @return 用户权限
     */
    private List<GrantedAuthority> getAuthorities(String userRoles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (StringUtils.isEmpty(userRoles)) {
            return grantedAuthorities;
        }
        String[] roles = userRoles.split(ROLE_SEPERATOR);
        for (String role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }

        return grantedAuthorities;
    }
}
