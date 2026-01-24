package com.filmeo.webapp.model.formEntity;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserForm {
    @NotNull
    @Size(min = 2)
    private String pseudo;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank
    /*@Pattern(regexp = "/^\\S{3,}$/",
            message = "The password mut be respect min 8 length, upper letter, lower letter, and special char and number")*/
    private String password;

    @NotNull
    private String confirmPassword;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }

}


/*
* /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{3,}$/
* */