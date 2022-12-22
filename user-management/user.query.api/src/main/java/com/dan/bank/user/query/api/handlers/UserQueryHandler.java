package com.dan.bank.user.query.api.handlers;

import com.dan.bank.user.query.api.dto.UserLookupResponse;
import com.dan.bank.user.query.api.queries.FindAllUsersQuery;
import com.dan.bank.user.query.api.queries.FindUserByIdQuery;
import com.dan.bank.user.query.api.queries.SearchUsersQuery;

public interface UserQueryHandler {
    UserLookupResponse getUserById(FindUserByIdQuery query);

    UserLookupResponse searchUsers(SearchUsersQuery query);

    UserLookupResponse getAllUsers(FindAllUsersQuery query);
}
