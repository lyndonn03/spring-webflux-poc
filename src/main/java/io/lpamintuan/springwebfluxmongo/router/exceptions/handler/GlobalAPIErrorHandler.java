package io.lpamintuan.springwebfluxmongo.router.exceptions.handler;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class GlobalAPIErrorHandler extends AbstractErrorWebExceptionHandler {

    public GlobalAPIErrorHandler(ErrorAttributes errorAttributes, Resources resources,
            ApplicationContext applicationContext, ServerCodecConfigurer codecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageReaders(codecConfigurer.getReaders());
        this.setMessageWriters(codecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), 
            req -> {
                Map<String, Object> errors = this.getErrorAttributes(req, ErrorAttributeOptions.defaults());
                int statusCode = (int) errors.get("status");
                return ServerResponse.status(statusCode).bodyValue(errors);
            }
        );
    }
    
}
