package com.dan.bank.user.cmd.api.aggregates;

import com.dan.bank.user.cmd.api.commands.RegisterUserCommand;
import com.dan.bank.user.cmd.api.commands.RemoveUserCommand;
import com.dan.bank.user.cmd.api.commands.UpdateUserCommand;
import com.dan.bank.user.cmd.api.security.DefaultPasswordEncoder;
import com.dan.bank.user.cmd.api.security.PasswordEncoder;
import com.dan.bank.user.core.events.UserRegisteredEvent;
import com.dan.bank.user.core.events.UserRemovedEvent;
import com.dan.bank.user.core.events.UserUpdatedEvent;
import com.dan.bank.user.core.models.Account;
import com.dan.bank.user.core.models.User;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;


@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder passwordEncoder;

    public UserAggregate(){
        passwordEncoder = new DefaultPasswordEncoder();
    }

    @CommandHandler
    public UserAggregate(final RegisterUserCommand registerUserCommand) {
        passwordEncoder = new DefaultPasswordEncoder();
        final User newUser = registerUserCommand.getUser();

        newUser.setId(registerUserCommand.getId());
        securePassword(newUser.getAccount());

        final UserRegisteredEvent userRegisteredEvent = UserRegisteredEvent.builder()
                .id(registerUserCommand.getId())
                .user(newUser)
                .build();

        AggregateLifecycle.apply(userRegisteredEvent);
    }

    @CommandHandler
    public void handle(final UpdateUserCommand updateUserCommand) {
        final User updatedUser = updateUserCommand.getUser();
        updatedUser.setId(updateUserCommand.getId());
        securePassword(updatedUser.getAccount());

        final UserUpdatedEvent userUpdatedEvent = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();

        AggregateLifecycle.apply(userUpdatedEvent);
    }

    @CommandHandler
    public void handle(final RemoveUserCommand removeUserCommand) {
        final UserRemovedEvent userRemovedEvent = new UserRemovedEvent();
        userRemovedEvent.setId(removeUserCommand.getId());

        AggregateLifecycle.apply(userRemovedEvent);
    }

    @EventSourcingHandler
    public void on(final UserRegisteredEvent userRegisteredEvent) {
        this.id = userRegisteredEvent.getId();
        this.user = userRegisteredEvent.getUser();
    }

    @EventSourcingHandler
    public void on(final UserUpdatedEvent userUpdatedEvent) {
        this.user = userUpdatedEvent.getUser();
    }

    @EventSourcingHandler
    public void on(final UserRemovedEvent userRemovedEvent) {
        AggregateLifecycle.markDeleted();
    }

    private void securePassword(final Account account){
        final String password = account.getPassword();
        final String hashedPassword = passwordEncoder.hashPassword(password);
        account.setPassword(hashedPassword);
    }

}

