package com.sirmeows.graphql.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handle(BookNotFoundException e) {
        return GraphqlErrorBuilder.newError()
                .message(e.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handle(AuthorNotFoundException e) {
        return GraphqlErrorBuilder.newError()
                .message(e.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handle(InvalidBookDataException e) {
        return GraphqlErrorBuilder.newError()
                .message(e.getMessage())
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handle(EntityNotFoundException e) {
        return GraphqlErrorBuilder.newError()
                .message(e.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handle(Exception e) {
        return GraphqlErrorBuilder.newError()
                .message(e.getMessage())
                .errorType(ErrorType.INTERNAL_ERROR)
                .build();
    }
}
