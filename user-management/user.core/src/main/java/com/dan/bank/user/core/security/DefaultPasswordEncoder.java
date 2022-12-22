package com.dan.bank.user.core.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultPasswordEncoder implements InternalPasswordEncoder {

    private BCryptPasswordEncoder passwordEncoder;

    public DefaultPasswordEncoder(){
        passwordEncoder = new BCryptPasswordEncoder(12);
    }
    @Override
    public String hashPassword(final String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

}
