package com.dan.bank.user.query.api.handlers;

import com.dan.bank.user.core.events.UserRegisteredEvent;
import com.dan.bank.user.core.events.UserRemovedEvent;
import com.dan.bank.user.core.events.UserUpdatedEvent;
import com.dan.bank.user.query.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("user-group")
@AllArgsConstructor
public class DefaultUserEventHandler implements UserEventHandler {

    private final UserRepository userRepository;

    @Override
    @EventHandler
    public void on(final UserRegisteredEvent event) {
        userRepository.save(event.getUser());
    }

    @Override
    @EventHandler
    public void on(final UserUpdatedEvent event) {
        userRepository.save(event.getUser());
    }

    @Override
    @EventHandler
    public void on(final UserRemovedEvent event) {
        userRepository.deleteById(event.getId());
    }
}
