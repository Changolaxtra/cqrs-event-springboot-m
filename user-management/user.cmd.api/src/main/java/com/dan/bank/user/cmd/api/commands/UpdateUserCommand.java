package com.dan.bank.user.cmd.api.commands;

import com.dan.bank.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCommand {

    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "no user details were supplied")
    private User user;
}
