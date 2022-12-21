package com.dan.bank.user.query.api.handlers;

import com.dan.bank.user.core.events.UserRegisteredEvent;
import com.dan.bank.user.core.events.UserRemovedEvent;
import com.dan.bank.user.core.events.UserUpdatedEvent;

public interface UserEventHandler {
    void on(UserRegisteredEvent event);

    void on(UserUpdatedEvent event);

    void on(UserRemovedEvent event);
}
