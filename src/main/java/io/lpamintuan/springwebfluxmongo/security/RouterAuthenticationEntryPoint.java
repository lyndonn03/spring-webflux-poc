package io.lpamintuan.springwebfluxmongo.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.lpamintuan.springwebfluxmongo.router.exceptions.APIException;
import reactor.core.publisher.Mono;

@Component
public class RouterAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.error(
            new APIException(ex.getMessage(), HttpStatus.UNAUTHORIZED)
        );
    }

   
}
