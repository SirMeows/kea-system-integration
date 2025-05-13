package com.sirmeows.graphql.exception;

import java.util.UUID;

public class AuthorNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Author not found";
    private static final String ID_MESSAGE = "Author not found with id: %s";

    public AuthorNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public AuthorNotFoundException(UUID id) {
        super(String.format(ID_MESSAGE, id));
    }
}
