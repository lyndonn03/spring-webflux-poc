package io.lpamintuan.springwebfluxmongo.router.exceptions;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException {

    private int statusCode;

    public APIException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    
}
