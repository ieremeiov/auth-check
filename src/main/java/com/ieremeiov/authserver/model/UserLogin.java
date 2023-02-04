package com.ieremeiov.authserver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserLogin {

    //    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
