package com.example.bookstore.models;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@RegisterForReflection
@Data
public class Book {
    private String isbn;
    private String title;
    private String author;
}