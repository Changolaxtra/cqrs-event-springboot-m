package com.dan.bank.user.query.api.handlers;

import com.dan.bank.user.core.models.User;
import com.dan.bank.user.query.api.dto.UserLookupResponse;
import com.dan.bank.user.query.api.queries.FindAllUsersQuery;
import com.dan.bank.user.query.api.queries.FindUserByIdQuery;
import com.dan.bank.user.query.api.queries.SearchUsersQuery;
import com.dan.bank.user.query.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DbUserQueryHandler implements UserQueryHandler {

    private final UserRepository userRepository;

    @Override
    @QueryHandler
    public UserLookupResponse getUserById(final FindUserByIdQuery query) {
        final List<User> users = new ArrayList<>();
        userRepository.findById(query.getId())
                .ifPresent(users::add);
        return new UserLookupResponse(Collections.unmodifiableList(users));
    }

    @Override
    @QueryHandler
    public UserLookupResponse searchUsers(final SearchUsersQuery query) {
        final List<User> users = userRepository.findByFilterRegex(query.getFilter());
        return new UserLookupResponse(Collections.unmodifiableList(users));
    }

    @Override
    @QueryHandler
    public UserLookupResponse getAllUsers(final FindAllUsersQuery query) {
        final List<User> users = userRepository.findAll();
        return new UserLookupResponse(Collections.unmodifiableList(users));
    }
}
