package io.lpamintuan.springwebfluxmongo.router._config;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import io.lpamintuan.springwebfluxmongo.router.exceptions.APIException;
import io.lpamintuan.springwebfluxmongo.router.exceptions.ObjectValidationException;

@Component
public class RouterErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errors = super.getErrorAttributes(request, options);
        Throwable ex = this.getError(request);
        if (ex instanceof APIException) {
            APIException aex = (APIException) ex;
            Map<String,String> validationErrors = this.getValidationErrors(aex);
            errors.put("error", aex.getStatus().getReasonPhrase());
            errors.put("status", aex.getStatus().value());
            if(validationErrors != null) errors.put("content", validationErrors);
        }
        errors.put("message", ex.getMessage());
        errors.remove("requestId");
        return errors;
    }

    protected Map<String, String> getValidationErrors(APIException ex) {
        if(ex instanceof ObjectValidationException)
            return ((ObjectValidationException) ex).getContent();
        return null;
    }

}
