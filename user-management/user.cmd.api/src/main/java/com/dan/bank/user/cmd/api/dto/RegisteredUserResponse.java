package com.dan.bank.user.cmd.api.dto;

public class RegisteredUserResponse extends BaseResponse {

    private String id;

    public RegisteredUserResponse(final String id, final String message) {
        super(message);
        this.id = id;
    }
}
