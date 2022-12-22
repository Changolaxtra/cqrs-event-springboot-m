package com.dan.bank.user.query.api.controllers;

import com.dan.bank.user.query.api.dto.UserLookupResponse;
import com.dan.bank.user.query.api.queries.FindAllUsersQuery;
import com.dan.bank.user.query.api.queries.FindUserByIdQuery;
import com.dan.bank.user.query.api.queries.SearchUsersQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/query/api/v1/user")
public class UserLookupController {

    private final QueryGateway queryGateway;

    @GetMapping
    public ResponseEntity<UserLookupResponse> getAllUsers() {
        try {
            final FindAllUsersQuery query = new FindAllUsersQuery();
            final UserLookupResponse response = queryGateway.query(query,
                    ResponseTypes.instanceOf(UserLookupResponse.class)).join();

            return handleUserLookupResponse(response);

        } catch (Exception exception) {
            final String errorMessage = "Failed to complete get all users request";
            log.error("getAllUsers Error", exception);
            return new ResponseEntity<>(new UserLookupResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserLookupResponse> getUserById(@PathVariable(value = "id") final String id) {
        try {
            final FindUserByIdQuery query = new FindUserByIdQuery(id);
            final UserLookupResponse response = queryGateway.query(query,
                    ResponseTypes.instanceOf(UserLookupResponse.class)).join();

            return handleUserLookupResponse(response);

        } catch (Exception exception) {
            final String errorMessage = "Failed to complete get User by Id request";
            log.error("getUserById Error", exception);
            return new ResponseEntity<>(new UserLookupResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/search/{filter}")
    public ResponseEntity<UserLookupResponse> searchUserByFilter(@PathVariable(value = "filter") final String filter) {
        try {
            final SearchUsersQuery query = new SearchUsersQuery(filter);
            final UserLookupResponse response = queryGateway.query(query,
                    ResponseTypes.instanceOf(UserLookupResponse.class)).join();

            return handleUserLookupResponse(response);

        } catch (Exception exception) {
            final String errorMessage = "Failed to complete search User by filter request";
            log.error("searchUserByFilter Error", exception);
            return new ResponseEntity<>(new UserLookupResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<UserLookupResponse> handleUserLookupResponse(final UserLookupResponse response) {
        if (response == null || response.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
