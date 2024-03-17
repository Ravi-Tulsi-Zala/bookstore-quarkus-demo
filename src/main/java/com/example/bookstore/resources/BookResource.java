package com.example.bookstore.resources;

import com.example.bookstore.services.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.net.URI;
import java.util.List;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import com.example.bookstore.models.Book;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "book", description = "Book Operations")
@AllArgsConstructor
@Slf4j
public class BookResource {

    private final BookService bookService;

    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get All Books",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = Book.class)
            )
    )
    public Response getAllBooks() {
        return Response.ok(bookService.getAllBooks()).build();
    }

    @GET
    @Path("/{isbn}")
    @APIResponse(
            responseCode = "200",
            description = "Get Book by bookId",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Book.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Book does not exist for bookId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response getBookById(@PathParam("isbn") String isbn) {
        return bookService.findByISBN(isbn)
                .map(book -> Response.ok(book).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


    @POST
    @APIResponse(
            responseCode = "201",
            description = "Book Created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Book.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid Book",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Book already exists for bookId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response post(@NotNull @Valid Book book, @Context UriInfo uriInfo) {
        bookService.saveBook(book);
        URI uri = uriInfo.getAbsolutePathBuilder().path(book.getIsbn()).build();
        return Response.created(uri).entity(book).build();
    }
}
