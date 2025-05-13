package com.sirmeows.graphql.resolver;

import com.sirmeows.graphql.entity.Author;
import com.sirmeows.graphql.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BookResolver {
    @SchemaMapping(typeName = "Book")
    public Author author(Book book) {
        return book.getAuthor();
    }
}

