package com.example.bookstore.repository;

import com.example.bookstore.models.BookEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookRepository implements PanacheRepositoryBase<BookEntity, String> {

}