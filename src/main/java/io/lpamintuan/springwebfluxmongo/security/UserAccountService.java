package io.lpamintuan.springwebfluxmongo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.lpamintuan.springwebfluxmongo.models.UserAccount;
import io.lpamintuan.springwebfluxmongo.repositories.UserAccountRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Primary
public class UserAccountService implements ReactiveUserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return this.userAccountRepository.findByUsername(username)
                .cast(UserDetails.class);
    }

    public Mono<UserAccount> createUserAccount(Mono<UserAccount> userAccount) {
        return userAccount
                .doOnNext(x -> x.setPassword(passwordEncoder.encode(x.getPassword())))
                .flatMap(userAccountRepository::insert);
    }

    public Flux<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }

}
