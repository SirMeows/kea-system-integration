package com.meows.sir.exception;

public class JsonSerialisationException extends RuntimeException {

    public JsonSerialisationException(String message) {
        super(message);
    }

    public JsonSerialisationException(String message, Throwable cause) {
        super(message, cause);
    }
}
