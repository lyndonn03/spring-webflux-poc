package io.lpamintuan.springwebfluxmongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
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
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http.csrf().disable();
        http.cors().disable();
        http.httpBasic();

        http.authorizeExchange()
            .pathMatchers(HttpMethod.POST, "/products").authenticated()
            .pathMatchers(HttpMethod.GET, "/products").permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(routerAuthenticationEntryPoint);
        
        return http.build();
    }
    
}
