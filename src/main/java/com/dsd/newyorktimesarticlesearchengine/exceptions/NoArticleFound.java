package com.dsd.newyorktimesarticlesearchengine.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoArticleFound extends RuntimeException {
    public NoArticleFound() {
        super();
    }

    public NoArticleFound(String message) {
        super(message);
    }

    public NoArticleFound(String message, Throwable cause) {
        super(message, cause);
    }
}