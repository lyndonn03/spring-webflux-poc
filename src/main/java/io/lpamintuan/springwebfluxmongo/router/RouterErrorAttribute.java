package io.lpamintuan.springwebfluxmongo.router;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import io.lpamintuan.springwebfluxmongo.router.exceptions.APIException;

@Component
public class RouterErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errors = super.getErrorAttributes(request, options);
        APIException ex = (APIException) this.getError(request);
        errors.remove("requestId");
        errors.put("message", ex.getMessage());
        errors.put("status", ex.getStatusCode());
        return errors;
    }
    
}
