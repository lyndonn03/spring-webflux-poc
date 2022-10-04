package io.lpamintuan.springwebfluxmongo.repositories;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.lpamintuan.springwebfluxmongo.models.UserAccount;
import reactor.core.publisher.Mono;

public interface UserAccountRepository extends ReactiveMongoRepository<UserAccount, String> {

    Mono<UserAccount> findByUsername(String username);

    @Query("{ 'username': ?0 }")
    Mono<UserAccount> findUserAccountByUsername(String username);
    
}
