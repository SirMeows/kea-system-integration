package com.sirmeows.graphql.resolver;

import com.sirmeows.graphql.entity.Book;
import com.sirmeows.graphql.service.BookService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MutationResolver {
    private final BookService bookService;

    @MutationMapping
    public Book createBook(
            @Argument @NotBlank String title,
            @Argument Integer releaseYear,
            @Argument UUID authorId
    ) {
        return bookService.createBook(title, releaseYear, authorId);
    }

    @MutationMapping
    public Book updateBook(
            @Argument UUID id,
            @Argument @NotBlank String title,
            @Argument Integer releaseYear,
            @Argument UUID authorId
    ) {
        return bookService.updateBook(id, title, releaseYear, authorId);
    }

    @MutationMapping
    public Map<String, String> deleteBook(@Argument UUID id) {
        bookService.deleteBook(id);
        return Map.of("message", "Book deleted successfully");
    }
}
