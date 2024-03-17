package com.example.bookstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity(name = "Book")
@Table(name = "books")
@Data
public class BookEntity {

    @Id
    @Column(name = "isbn")
    @NotEmpty(message = "{Book.isbn.required}")
    private String isbn;

    @Column(name = "title")
    @NotEmpty(message = "{Book.title.required}")
    private String title;

    @Column(name = "author")
    private String author;


}