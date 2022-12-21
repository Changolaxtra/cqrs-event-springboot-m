package com.dan.bank.user.cmd.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisteredUserResponse extends BaseResponse {

    private String id;

    public RegisteredUserResponse(final String id, final String message) {
        super(message);
        this.id = id;
    }
}
