package io.lpamintuan.springwebfluxmongo.templates;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginTemplate {

    @NotEmpty(message = "Username must not be empty.")
    private String username;

    @NotEmpty(message = "Username must not be empty.")
    private String password;
    
}
