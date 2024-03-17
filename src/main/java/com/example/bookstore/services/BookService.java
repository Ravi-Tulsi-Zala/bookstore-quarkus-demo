package com.example.bookstore.services;

import com.example.bookstore.mappers.BookMapper;
import com.example.bookstore.models.BookEntity;
import com.example.bookstore.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.bookstore.models.Book;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<Book> getAllBooks() {
        return this.bookMapper.toDomainList(bookRepository.findAll().list());
    }

    public Optional<Book> findByISBN(String isbn) {
        return bookRepository.findByIdOptional(isbn)
                .map(bookMapper::toDomain);
    }

    @Transactional
    public void saveBook(Book book) {
        log.debug("Saving Book: {}", book);
        BookEntity entity = bookMapper.toEntity(book);
        bookRepository.persist(entity);
        bookMapper.updateDomainFromEntity(entity, book);
    }
}
