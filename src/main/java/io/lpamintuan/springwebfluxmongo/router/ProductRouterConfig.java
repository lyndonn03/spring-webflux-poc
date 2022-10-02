package io.lpamintuan.springwebfluxmongo.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.lpamintuan.springwebfluxmongo.router.handlers.ProductRouteHandlers;

@Configuration
public class ProductRouterConfig {

    @Autowired
    private ProductRouteHandlers productRouteHandlers;

    @Bean
    RouterFunction<ServerResponse> productRoutes() {

        return RouterFunctions.route()
                    .GET("/products", productRouteHandlers::getProducts)
                    .POST("/products", productRouteHandlers::postProduct)
                    .build();

    }

    
}
