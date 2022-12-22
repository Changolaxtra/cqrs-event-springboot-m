package com.dan.bank.user.cmd.api.aggregates;

import com.dan.bank.user.cmd.api.commands.RegisterUserCommand;
import com.dan.bank.user.cmd.api.commands.RemoveUserCommand;
import com.dan.bank.user.cmd.api.commands.UpdateUserCommand;
import com.dan.bank.user.core.security.DefaultPasswordEncoder;
import com.dan.bank.user.core.security.InternalPasswordEncoder;
import com.dan.bank.user.core.events.UserRegisteredEvent;
import com.dan.bank.user.core.events.UserRemovedEvent;
import com.dan.bank.user.core.events.UserUpdatedEvent;
import com.dan.bank.user.core.models.Account;
import com.dan.bank.user.core.models.User;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;


@Aggregate
@Slf4j
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;

    private final InternalPasswordEncoder internalPasswordEncoder;

    public UserAggregate(){
        internalPasswordEncoder = new DefaultPasswordEncoder();
    }

    @CommandHandler
    public UserAggregate(final RegisterUserCommand command) {
        internalPasswordEncoder = new DefaultPasswordEncoder();
        final User newUser = command.getUser();

        newUser.setId(command.getId());
        securePassword(newUser.getAccount());

        final UserRegisteredEvent userRegisteredEvent = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();

        log.info("Dispatching event {}:{}", userRegisteredEvent.getClass().getSimpleName(), command.getId());
        AggregateLifecycle.apply(userRegisteredEvent);
    }

    @CommandHandler
    public void handle(final UpdateUserCommand command) {
        final User updatedUser = command.getUser();
        updatedUser.setId(command.getId());
        securePassword(updatedUser.getAccount());

        final UserUpdatedEvent userUpdatedEvent = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updatedUser)
                .build();

        log.info("Dispatching event {}:{}", userUpdatedEvent.getClass().getSimpleName(), command.getId());
        AggregateLifecycle.apply(userUpdatedEvent);
    }

    @CommandHandler
    public void handle(final RemoveUserCommand command) {
        final UserRemovedEvent userRemovedEvent = new UserRemovedEvent();
        userRemovedEvent.setId(command.getId());

        log.info("Dispatching event {}:{}", userRemovedEvent.getClass().getSimpleName(), command.getId());
        AggregateLifecycle.apply(userRemovedEvent);
    }

    @EventSourcingHandler
    public void on(final UserRegisteredEvent event) {
        log.info("OnEvent {}:{}", event.getClass().getSimpleName(), event.getId());
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(final UserUpdatedEvent event) {
        log.info("OnEvent {}:{}", event.getClass().getSimpleName(), event.getId());
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(final UserRemovedEvent event) {
        log.info("OnEvent {}:{}", event.getClass().getSimpleName(), event.getId());
        AggregateLifecycle.markDeleted();
    }

    private void securePassword(final Account account){
        final String password = account.getPassword();
        final String hashedPassword = internalPasswordEncoder.hashPassword(password);
        account.setPassword(hashedPassword);
    }

}

