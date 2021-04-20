package com.dsd.newyorktimesarticlesearchengine.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentFound extends RuntimeException {
    public NoContentFound() {
        super();
    }

    public NoContentFound(String message) {
        super(message);
    }

    public NoContentFound(String message, Throwable cause) {
        super(message, cause);
    }
}