package com.sirmeows.graphql.exception;

public class InvalidBookDataException extends RuntimeException {
    private static final String EMPTY_TITLE_MESSAGE = "Book title cannot be empty";
    private static final String INVALID_YEAR_MESSAGE = "Invalid release year";
    private static final String NULL_AUTHOR_MESSAGE = "Author ID cannot be null";

    public static InvalidBookDataException emptyTitle() {
        return new InvalidBookDataException(EMPTY_TITLE_MESSAGE);
    }

    public static InvalidBookDataException invalidYear() {
        return new InvalidBookDataException(INVALID_YEAR_MESSAGE);
    }

    public static InvalidBookDataException nullAuthor() {
        return new InvalidBookDataException(NULL_AUTHOR_MESSAGE);
    }

    private InvalidBookDataException(String message) {
        super(message);
    }
}
