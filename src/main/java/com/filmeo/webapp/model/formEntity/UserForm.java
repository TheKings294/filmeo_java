package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class UserForm {
    @NotBlank(message = "The pseudo can not be null")
    @Size(min = 2, message = "The pseudo ned to be 2 or more than 2")
    private String pseudo;

    @NotBlank(message = "The email can not be null")
    @Email
    private String email;

    @NotNull
    private List<String> roles;

    @NotBlank(message = "The password is required")
    /*@Pattern(regexp = "/^\\S{3,}$/",
            message = "The password mut be respect min 8 length, upper letter, lower letter, and special char and number")*/
    @Size(min = 8)
    private String password;

    @NotBlank(message = "The password confirmation is required")
    private String confirmPassword;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}


/*
* /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{3,}$/
* */