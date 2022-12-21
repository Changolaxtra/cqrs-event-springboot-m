package com.dan.bank.user.query.api.handlers;

import com.dan.bank.user.core.events.UserRegisteredEvent;
import com.dan.bank.user.core.events.UserRemovedEvent;
import com.dan.bank.user.core.events.UserUpdatedEvent;
import com.dan.bank.user.query.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("user-group")
@AllArgsConstructor
@Slf4j
public class DefaultUserEventHandler implements UserEventHandler {

    private final UserRepository userRepository;

    @Override
    @EventHandler
    public void on(final UserRegisteredEvent event) {
        log.info("Saving event: {}", event.getId());
        userRepository.save(event.getUser());
    }

    @Override
    @EventHandler
    public void on(final UserUpdatedEvent event) {
        log.info("Update event: {}", event.getId());
        userRepository.save(event.getUser());
    }

    @Override
    @EventHandler
    public void on(final UserRemovedEvent event) {
        log.info("Remove event: {}", event.getId());
        userRepository.deleteById(event.getId());
    }
}
