package com.dan.bank.user.cmd.api.controllers;

import com.dan.bank.user.cmd.api.commands.RegisterUserCommand;
import com.dan.bank.user.cmd.api.commands.RemoveUserCommand;
import com.dan.bank.user.cmd.api.commands.UpdateUserCommand;
import com.dan.bank.user.cmd.api.dto.BaseResponse;
import com.dan.bank.user.cmd.api.dto.RegisteredUserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user")
@Slf4j
public class UserController {

    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<RegisteredUserResponse> registerUser(@Valid @RequestBody RegisterUserCommand command) {
        final String id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new RegisteredUserResponse(id, "User registered"),
                    HttpStatus.CREATED);
        } catch (Exception exception) {
            final String errorMessage = "Error processing register User request for id: " + id;
            log.error("RegisterUser Error", exception);
            return new ResponseEntity<>(new RegisteredUserResponse(id, errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updatedUser(
            @PathVariable(value = "id") final String id,
            @Valid @RequestBody final UpdateUserCommand command) {

        try {
           command.setId(id);
           commandGateway.send(command);

           return new ResponseEntity<>(new BaseResponse("User successfully updated"),  HttpStatus.OK);
        } catch (Exception exception) {
            final String errorMessage = "Error processing update User request for id: " + id;
            log.error("UpdateUser Error", exception);
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> removeUser(@PathVariable(value = "id") final String id){
        try {
            commandGateway.send(new RemoveUserCommand(id));
            return new ResponseEntity<>(new BaseResponse("User successfully removed"),  HttpStatus.OK);
        } catch (Exception exception) {
            final String errorMessage = "Error processing remove User request for id: " + id;
            log.error("RemoveUser Error", exception);
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
