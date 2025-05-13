package com.sirmeows.graphql;

import com.sirmeows.graphql.entity.Author;
import com.sirmeows.graphql.entity.Book;
import com.sirmeows.graphql.repository.AuthorRepository;
import com.sirmeows.graphql.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @PostConstruct
    public void initializeData() {
        log.info("Starting to initialize sample library data...");

        if (authorRepository.count() > 0) {
            log.info("Database already contains data, skipping initialization");
            return;
        }

        var authors = createAuthors();
        var savedAuthors = authorRepository.saveAll(authors);

        var allBooks = createBooks(savedAuthors);
        bookRepository.saveAll(allBooks);

        log.info("Finished initializing library data. Created {} authors and {} books",
                savedAuthors.size(), allBooks.size());
    }

    private List<Author> createAuthors() {
        return List.of(
                Author.builder().name("George R.R. Martin").build(),
                Author.builder().name("Brandon Sanderson").build(),
                Author.builder().name("Neil Gaiman").build(),
                Author.builder().name("Terry Pratchett").build(),
                Author.builder().name("Patrick Rothfuss").build(),
                Author.builder().name("Stephen King").build(),
                Author.builder().name("Margaret Atwood").build(),
                Author.builder().name("Ursula K. Le Guin").build(),
                Author.builder().name("Frank Herbert").build(),
                Author.builder().name("Robin Hobb").build()
        );
    }

    private List<Book> createBooks(List<Author> authors) {
        List<Book> books = new ArrayList<>();

        // Get author by name helper
        Map<String, Author> authorMap = authors.stream()
                .collect(Collectors.toMap(Author::getName, author -> author));

        // Create books with their actual authors and years
        books.add(Book.builder().title("A Game of Thrones").releaseYear(1996).author(authorMap.get("George R.R. Martin")).build());
        books.add(Book.builder().title("A Clash of Kings").releaseYear(1998).author(authorMap.get("George R.R. Martin")).build());
        books.add(Book.builder().title("Mistborn: The Final Empire").releaseYear(2006).author(authorMap.get("Brandon Sanderson")).build());
        books.add(Book.builder().title("The Way of Kings").releaseYear(2010).author(authorMap.get("Brandon Sanderson")).build());
        books.add(Book.builder().title("American Gods").releaseYear(2001).author(authorMap.get("Neil Gaiman")).build());
        books.add(Book.builder().title("Neverwhere").releaseYear(1996).author(authorMap.get("Neil Gaiman")).build());
        books.add(Book.builder().title("Good Omens").releaseYear(1990).author(authorMap.get("Terry Pratchett")).build());
        books.add(Book.builder().title("Guards! Guards!").releaseYear(1989).author(authorMap.get("Terry Pratchett")).build());
        books.add(Book.builder().title("The Name of the Wind").releaseYear(2007).author(authorMap.get("Patrick Rothfuss")).build());
        books.add(Book.builder().title("The Wise Man's Fear").releaseYear(2011).author(authorMap.get("Patrick Rothfuss")).build());
        books.add(Book.builder().title("The Shining").releaseYear(1977).author(authorMap.get("Stephen King")).build());
        books.add(Book.builder().title("It").releaseYear(1986).author(authorMap.get("Stephen King")).build());
        books.add(Book.builder().title("The Handmaid's Tale").releaseYear(1985).author(authorMap.get("Margaret Atwood")).build());
        books.add(Book.builder().title("Oryx and Crake").releaseYear(2003).author(authorMap.get("Margaret Atwood")).build());
        books.add(Book.builder().title("A Wizard of Earthsea").releaseYear(1968).author(authorMap.get("Ursula K. Le Guin")).build());
        books.add(Book.builder().title("The Left Hand of Darkness").releaseYear(1969).author(authorMap.get("Ursula K. Le Guin")).build());
        books.add(Book.builder().title("Dune").releaseYear(1965).author(authorMap.get("Frank Herbert")).build());
        books.add(Book.builder().title("Dune Messiah").releaseYear(1969).author(authorMap.get("Frank Herbert")).build());
        books.add(Book.builder().title("Assassin's Apprentice").releaseYear(1995).author(authorMap.get("Robin Hobb")).build());
        books.add(Book.builder().title("Royal Assassin").releaseYear(1996).author(authorMap.get("Robin Hobb")).build());

        return books;
    }

}

