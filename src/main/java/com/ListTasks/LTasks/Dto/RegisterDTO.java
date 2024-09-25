package com.ListTasks.LTasks.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for user registration")


public class RegisterDTO {
    @Schema(description = "Full name of the user", example = "John Doe", required = true)

    private String fullName;
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String email;
    @Schema(description = "Password for the user's account", example = "P@ssw0rd", required = true)
    private String password;
}
