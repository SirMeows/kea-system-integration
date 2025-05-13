package com.sirmeows.graphql.resolver;

import com.sirmeows.graphql.entity.Author;
import com.sirmeows.graphql.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorResolver {
    @SchemaMapping(typeName = "Author")
    public List<Book> books(Author author) {
        return author.getBooks();
    }
}

