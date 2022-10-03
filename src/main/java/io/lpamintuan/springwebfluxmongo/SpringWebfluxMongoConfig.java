package io.lpamintuan.springwebfluxmongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

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
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http.csrf().disable();
        http.cors().disable();
        http.httpBasic();

        http.authorizeExchange()
            .pathMatchers(HttpMethod.POST, "/products").authenticated()
            .pathMatchers(HttpMethod.POST, "/users").permitAll()
            .pathMatchers(HttpMethod.GET, "/products", "/users").permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(routerAuthenticationEntryPoint);
        
        return http.build();
    }
    
}
