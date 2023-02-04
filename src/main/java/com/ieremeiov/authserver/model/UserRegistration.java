package com.ieremeiov.authserver.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;


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
