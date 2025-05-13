package com.sirmeows.graphql.exception;

import java.util.UUID;

public class BookNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Book not found";
    private static final String ID_MESSAGE = "Book not found with id: %s";

    public BookNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public BookNotFoundException(UUID id) {
        super(String.format(ID_MESSAGE, id));
    }
}
