package io.lpamintuan.springwebfluxmongo.templates;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtLoginTemplate {

    private String token;
    private String expiration;
    private String created;
    
}
