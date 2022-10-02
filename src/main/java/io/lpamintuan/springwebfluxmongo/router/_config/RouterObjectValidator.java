package io.lpamintuan.springwebfluxmongo.router._config;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import io.lpamintuan.springwebfluxmongo.router.exceptions.ObjectValidationException;
import reactor.core.publisher.Mono;

@Component
public class RouterObjectValidator<T> {

    @Autowired
    private Validator validator;

    public Mono<T> validate(T obj) {
        return Mono.just(new BeanPropertyBindingResult(obj, obj.getClass().getName()))
                .doOnNext(x -> validator.validate(obj, x))
                .flatMap(x -> {
                    if (x.hasErrors()) {
                        Map<String, String> errorMap = x.getFieldErrors().stream()
                                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                                        (existing, replacement) -> existing));
                        return Mono.error(
                                new ObjectValidationException("Validation Error", HttpStatus.BAD_REQUEST, errorMap));
                    }
                    return Mono.just(obj);
                });
    }

}
