package com.dan.bank.user.cmd.api.configuration;

import com.dan.bank.user.cmd.api.commands.RegisterUserCommand;
import com.dan.bank.user.core.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
@AllArgsConstructor
public class InitialUserInitializer implements CommandLineRunner {

    private final PropertyUser propertyUser;
    private final CommandGateway commandGateway;

    @Override
    public void run(String... args) throws Exception {
        final User user = propertyUser.getUser();
        if (user == null) {
            throw new RuntimeException("Initial User is null");
        }
        try {
            log.info("Creating initial user...");
            final RegisterUserCommand command = new RegisterUserCommand();
            user.setId(UUID.randomUUID().toString());
            command.setId(user.getId());
            command.setUser(user);
            commandGateway.sendAndWait(command);
        } catch (Exception exception) {
            log.info("Error creating initial user", exception);
            throw new RuntimeException(exception);
        }
    }

}
