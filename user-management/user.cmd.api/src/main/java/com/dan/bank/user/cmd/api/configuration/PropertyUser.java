package com.dan.bank.user.cmd.api.configuration;

import com.dan.bank.user.core.models.User;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Data
@Component
@ConstructorBinding
@ConfigurationProperties(prefix = "initial")
public class PropertyUser  {
    private User user;
}
