package com.example;

import com.example.bookstore.models.Book;
import com.example.bookstore.resources.BookResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import java.util.ResourceBundle;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestHTTPEndpoint(BookResource.class)
public class BookResourceTest {

    @Test
    public void getAll() {
        given()
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    public void getById() {
        Book book = createBook();
        Book saved = given()
                .contentType(ContentType.JSON)
                .body(book)
                .post()
                .then()
                .statusCode(201)
                .extract().as(Book.class);
        Book got = given()
                .when()
                .get("/{isbn}", saved.getIsbn())
                .then()
                .statusCode(200)
                .extract().as(Book.class);
        assertThat(saved).isEqualTo(got);
    }

    @Test
    public void getByIdNotFound() {
        given()
                .when()
                .get("/{isbn}", 987654321)
                .then()
                .statusCode(404);
    }

    @Test
    public void post() {
        Book book = createBook();
        Book saved = given()
                .contentType(ContentType.JSON)
                .body(book)
                .post()
                .then()
                .statusCode(201)
                .extract().as(Book.class);
        assertThat(saved.getIsbn()).isNotNull();
    }

    private Book createBook() {
        Book book = new Book();
        book.setIsbn("1234192929121");
        book.setAuthor(RandomStringUtils.randomAlphabetic(10));
        book.setTitle(RandomStringUtils.randomAlphabetic(10));
        return book;
    }

    private String getErrorMessage(String key) {
        return ResourceBundle.getBundle("ValidationMessages").getString(key);
    }

}