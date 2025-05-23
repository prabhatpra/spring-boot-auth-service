package com.prabhat.aouth.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "Username cannot be blank")
    private String userName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;  // ✅ fixed naming

    @NotBlank(message = "Confirm password cannot be blank")
    private String confirmPassword;
}
