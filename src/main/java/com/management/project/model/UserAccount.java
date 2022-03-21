package com.management.project.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_accounts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username")
    @NotBlank(message = "Must enter user name")
    @Size(min = 5, max = 50, message = "User name must lie between {min} to {max} characters")
    private String userName;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotBlank(message = "Must enter password")
    @Size(min = 5, message = "Password must be at least {min} characters long")
    private String password;

    @NotBlank(message = "Must enter password confirm")
    @Size(min = 5, message = "Password Confirm must be at least {min} characters long")
    @Transient
    private String passwordConfirm;

    @Column
    @NotNull(message = "Select a role")
    private String role;

    @Column
    private boolean enabled;
}
