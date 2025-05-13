package com.sirmeows.graphql.service;

import com.sirmeows.graphql.entity.Author;
import com.sirmeows.graphql.entity.Book;
import com.sirmeows.graphql.exception.AuthorNotFoundException;
import com.sirmeows.graphql.exception.BookNotFoundException;
import com.sirmeows.graphql.exception.InvalidBookDataException;
import com.sirmeows.graphql.repository.AuthorRepository;
import com.sirmeows.graphql.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book createBook(String title, Integer releaseYear, UUID authorId) {
        validateInput(title, releaseYear, authorId);

        var author = getAuthorOrThrow(authorId);


        var book = Book.builder()
                .title(title)
                .releaseYear(releaseYear)
                .author(author)
                .build();

        return bookRepository.save(book);
    }

    private void validateInput(String title, Integer releaseYear, UUID authorId) {
        if (isEmptyTitle(title)) {
            throw InvalidBookDataException.emptyTitle();
        }
        if (isInvalidReleaseYear(releaseYear)) {
            throw InvalidBookDataException.invalidYear();
        }
        if (authorId == null) {
            throw InvalidBookDataException.nullAuthor();
        }
    }

    private Author getAuthorOrThrow(UUID authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));
    }

    private boolean isInvalidReleaseYear(Integer releaseYear) {
        return releaseYear == null || releaseYear < 0;
    }

    private boolean isEmptyTitle(String title) {
        return title == null || title.trim().isEmpty();
    }

    public Book updateBook(UUID id, String title, Integer releaseYear, UUID authorId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        validateInput(title, releaseYear, authorId);

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        book.setTitle(title);
        book.setReleaseYear(releaseYear);
        book.setAuthor(author);

        return bookRepository.save(book);
    }

    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
}