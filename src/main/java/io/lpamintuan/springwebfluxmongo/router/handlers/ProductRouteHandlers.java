package io.lpamintuan.springwebfluxmongo.router.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.lpamintuan.springwebfluxmongo.models.Product;
import io.lpamintuan.springwebfluxmongo.repositories.ProductRepository;
import io.lpamintuan.springwebfluxmongo.router._config.RouterObjectValidator;
import reactor.core.publisher.Mono;

@Component
public class ProductRouteHandlers {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RouterObjectValidator<Product> routerObjectValidator;

    public Mono<ServerResponse> getProducts(ServerRequest request) {

        return ServerResponse.ok().body(productRepository.findAll(), Product.class);

    }

    public Mono<ServerResponse> postProduct(ServerRequest request) {

        return request.bodyToFlux(Product.class)
                .flatMap(x ->  productRepository.insert(routerObjectValidator.validate(x)))
                .flatMap(x -> ServerResponse.ok().bodyValue(x))
                .next();

    }

}
