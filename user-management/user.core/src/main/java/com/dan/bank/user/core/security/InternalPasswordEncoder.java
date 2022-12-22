package com.dan.bank.user.core.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface InternalPasswordEncoder {
    String hashPassword(final String password);
    PasswordEncoder getPasswordEncoder();
}
