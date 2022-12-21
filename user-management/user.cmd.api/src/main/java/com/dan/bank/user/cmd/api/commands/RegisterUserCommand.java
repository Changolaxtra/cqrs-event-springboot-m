package com.dan.bank.user.cmd.api.commands;

import com.dan.bank.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand {

    @TargetAggregateIdentifier
    private String id;
    private User user;
}
