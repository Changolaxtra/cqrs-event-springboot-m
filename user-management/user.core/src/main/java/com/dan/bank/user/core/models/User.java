package com.dan.bank.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "users")
public class User {
    @Id
    private String id;
    @NotEmpty(message = "Firstname is mandatory")
    private String firstName;
    @NotEmpty(message = "Firstname is mandatory")
    private String lastName;
    @Email(message = "Please provide valid email address")
    private String email;
    @NotNull(message = "Please provide account credentials")
    private Account account;
}
