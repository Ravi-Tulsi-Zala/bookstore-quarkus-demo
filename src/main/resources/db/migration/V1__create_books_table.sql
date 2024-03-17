-- V1__create_books_table.sql

CREATE TABLE books (
                       isbn VARCHAR(13) PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
);