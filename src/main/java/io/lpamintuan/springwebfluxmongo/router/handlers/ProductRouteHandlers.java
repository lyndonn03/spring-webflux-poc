package io.lpamintuan.springwebfluxmongo.router.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.lpamintuan.springwebfluxmongo.models.Product;
import io.lpamintuan.springwebfluxmongo.repositories.ProductRepository;
import io.lpamintuan.springwebfluxmongo.router.exceptions.APIException;
import reactor.core.publisher.Mono;

@Component
public class ProductRouteHandlers {

    @Autowired
    private ProductRepository productRepository;

    public Mono<ServerResponse> getProducts(ServerRequest request) {

        return ServerResponse.ok().body(productRepository.findAll(), Product.class);
        
    }

    public Mono<ServerResponse> postProduct(ServerRequest request) {

        return request.bodyToFlux(Product.class)
                    .flatMap(x -> {
                        if(x.getName() == null || x.getName().isEmpty())
                            return Mono.error(new APIException("Name must not be null.", HttpStatus.BAD_REQUEST.value()));
                        return productRepository.insert(x)
                                    .then(ServerResponse.ok().bodyValue(x));
                    })
                    .next();

    }
    
}
