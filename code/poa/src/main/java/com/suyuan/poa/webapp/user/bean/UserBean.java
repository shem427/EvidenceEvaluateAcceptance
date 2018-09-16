package com.suyuan.poa.webapp.user.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suyuan.poa.webapp.common.CommonBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserBean extends CommonBean implements UserDetails {
    private int userId;
    private String policeNumber;
    private String name;
    private String phoneNumber;
    private String userRoles;

    private int deptId;
    private String deptName;

    @JsonIgnore
    private String password;

    private List<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return policeNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
