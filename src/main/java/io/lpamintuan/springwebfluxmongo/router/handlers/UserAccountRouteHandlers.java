package io.lpamintuan.springwebfluxmongo.router.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.lpamintuan.springwebfluxmongo.models.UserAccount;
import io.lpamintuan.springwebfluxmongo.router._config.RouterObjectValidator;
import io.lpamintuan.springwebfluxmongo.security.UserAccountService;
import reactor.core.publisher.Mono;

@Component
public class UserAccountRouteHandlers {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private RouterObjectValidator<UserAccount> routerObjectValidator;

    public Mono<ServerResponse> createUserAccount(ServerRequest request) {
        return request.bodyToMono(UserAccount.class)
                .flatMap(x -> {
                    System.out.println(x);
                    return userAccountService.createUserAccount(routerObjectValidator.validate(x));
                })
                .flatMap(x -> ServerResponse.ok().bodyValue(x));
    }

    public Mono<ServerResponse> getAllUserAccounts(ServerRequest request) {
        return ServerResponse.ok().body(userAccountService.getAllUserAccounts(), UserAccount.class);
    }
    
}
