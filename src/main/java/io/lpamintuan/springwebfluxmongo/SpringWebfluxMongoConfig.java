package io.lpamintuan.springwebfluxmongo;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.lpamintuan.springwebfluxmongo.security.RouterAuthenticationEntryPoint;

@Configuration
@EnableWebFluxSecurity
public class SpringWebfluxMongoConfig {

    @Autowired
    private RouterAuthenticationEntryPoint routerAuthenticationEntryPoint;

    @Bean
    public Resources resources() {
        return new Resources();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecretKey jjwtKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http.csrf().disable();
        http.cors().disable();

        http.formLogin();
        http.httpBasic();

        http.authorizeExchange()
            .pathMatchers(HttpMethod.POST, "/products").authenticated()
            .pathMatchers(HttpMethod.GET, "/profile").authenticated()
            .pathMatchers(HttpMethod.POST, "/users", "/signin").permitAll()
            .pathMatchers(HttpMethod.GET, "/products", "/users", "/users/**").permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(routerAuthenticationEntryPoint);
        
        return http.build();
    }
    
}
