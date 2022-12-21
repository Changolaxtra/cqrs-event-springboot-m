package com.dan.bank.user.cmd.api.security;

public interface PasswordEncoder {
    String hashPassword(final String password);
}
