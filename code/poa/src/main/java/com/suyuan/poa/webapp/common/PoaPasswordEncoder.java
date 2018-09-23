package com.suyuan.poa.webapp.common;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PoaPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return PoaUtil.md5Encode(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(rawPassword)
                || encodedPassword.equals(PoaUtil.md5Encode(rawPassword.toString()));
    }
}
