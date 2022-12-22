package com.dan.bank.user.query.api.dto;

import com.dan.bank.user.core.dto.BaseResponse;
import com.dan.bank.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLookupResponse extends BaseResponse {
    private List<User> users;

    public UserLookupResponse(final String message) {
        super(message);
    }

    public boolean isEmpty() {
        return users == null || users.isEmpty();
    }
}
