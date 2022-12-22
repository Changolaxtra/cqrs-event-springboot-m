package com.dan.bank.user.query.api.dto;

import com.dan.bank.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserLookupResponse {
    private List<User> users;
}
