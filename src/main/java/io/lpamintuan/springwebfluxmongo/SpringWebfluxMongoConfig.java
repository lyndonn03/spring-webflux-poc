package io.lpamintuan.springwebfluxmongo;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringWebfluxMongoConfig {

    @Bean
    public Resources resources() {
        return new Resources();
    }
    
}
