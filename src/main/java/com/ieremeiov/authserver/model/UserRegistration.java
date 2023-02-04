package com.ieremeiov.authserver.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Builder(builderMethodName = "with")
public class UserRegistration {

    //    @Email
    @NotBlank
    private final String email;

    @NotEmpty
    @Size(min = 4, max = 30)
    private final String password;

}
