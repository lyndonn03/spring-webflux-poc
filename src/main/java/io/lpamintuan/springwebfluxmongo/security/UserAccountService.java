package io.lpamintuan.springwebfluxmongo.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.lpamintuan.springwebfluxmongo.models.UserAccount;
import io.lpamintuan.springwebfluxmongo.repositories.UserAccountRepository;
import io.lpamintuan.springwebfluxmongo.router.exceptions.APIException;
import io.lpamintuan.springwebfluxmongo.templates.JwtLoginTemplate;
import io.lpamintuan.springwebfluxmongo.templates.UserLoginTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Primary
public class UserAccountService implements ReactiveUserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecretKey jjwtKey;

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

    public Mono<JwtLoginTemplate> signinUser(Mono<UserLoginTemplate> userCredentials) {
        return userCredentials
                .flatMap(x -> {
                    return userAccountRepository.findByUsername(x.getUsername())
                            .flatMap(z -> {
                                if (passwordEncoder.matches(x.getPassword(), z.getPassword()))
                                    return Mono.just(z);
                                return Mono.error(new APIException("Authentication Failed", HttpStatus.BAD_REQUEST));
                            });
                })
                .flatMap(x -> {
                    return Mono.just(Jwts.builder())
                            .map(z -> {
                                Date date = new Date();
                                Date exp = new Date();
                                exp.setTime(date.getTime() + 120000L);
                                String jwt = z.signWith(jjwtKey)
                                    .setSubject(x.getUsername()).setIssuedAt(date).setExpiration(exp).compact();
                                return new JwtLoginTemplate(jwt, exp.toString(), date.toString());
                            })
                            .subscribeOn(Schedulers.parallel());
                });
    }

}
