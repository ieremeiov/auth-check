package com.ieremeiov.authserver.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserLogin {

    //    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
