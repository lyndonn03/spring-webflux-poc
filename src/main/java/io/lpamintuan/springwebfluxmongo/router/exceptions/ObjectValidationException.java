package io.lpamintuan.springwebfluxmongo.router.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ObjectValidationException extends APIException {

    private Map<String, String> content;

    public ObjectValidationException(String message, HttpStatus status, Map<String, String> content) {
        super(message, status);
        this.content = content;
    }

    
    
}
