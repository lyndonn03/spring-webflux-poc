package io.lpamintuan.springwebfluxmongo.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.lpamintuan.springwebfluxmongo.router.handlers.UserAccountRouteHandlers;

@Configuration
public class UserAccountRouterConfig {

    @Autowired
    private UserAccountRouteHandlers handlers;

    @Bean
    RouterFunction<ServerResponse> userAccountRouterFunction() {
        return RouterFunctions.route()
                .POST("/users", handlers::createUserAccount)
                .GET("/users", handlers::getAllUserAccounts)
                .GET("/users/{id}", handlers::getUserAccount)
                .GET("/profile", handlers::getUserProfile)
                .build();
    }
    
}
