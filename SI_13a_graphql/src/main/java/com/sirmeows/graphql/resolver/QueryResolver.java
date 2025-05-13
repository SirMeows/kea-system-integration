package com.sirmeows.graphql.resolver;

import com.sirmeows.graphql.entity.Author;
import com.sirmeows.graphql.entity.Book;
import com.sirmeows.graphql.service.AuthorService;
import com.sirmeows.graphql.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class QueryResolver {
    private final BookService bookService;
    private final AuthorService authorService;

    @QueryMapping
    public List<Book> books() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book book(@Argument UUID id) {
        return bookService.getBookById(id);
    }

    @QueryMapping
    public List<Author> authors() {
        return authorService.getAllAuthors();
    }

    @QueryMapping
    public Author author(@Argument UUID id) {
        return authorService.getAuthorById(id);
    }
}

