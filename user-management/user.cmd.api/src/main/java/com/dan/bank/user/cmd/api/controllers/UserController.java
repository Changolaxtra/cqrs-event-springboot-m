package com.dan.bank.user.cmd.api.controllers;

import com.dan.bank.user.cmd.api.commands.RegisterUserCommand;
import com.dan.bank.user.cmd.api.dto.RegisteredUserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user")
@Slf4j
public class UserController {

    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<RegisteredUserResponse> registerUser(@RequestBody RegisterUserCommand command){
        command.setId(UUID.randomUUID().toString());
        try{
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new RegisteredUserResponse("User registered with id: " + command.getId()),
                    HttpStatus.CREATED);
        }catch (Exception exception){
            final String errorMessage = "Error processing register User request for id: " + command.getId();
            log.error("RegisterUser Error", exception);
            return new ResponseEntity<>(new RegisteredUserResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
