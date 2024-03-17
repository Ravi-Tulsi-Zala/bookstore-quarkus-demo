package com.example.bookstore.mappers;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.BookEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface BookMapper {

    List<Book> toDomainList(List<BookEntity> entities);

    Book toDomain(BookEntity entity);

    @InheritInverseConfiguration(name = "toDomain")
    BookEntity toEntity(Book domain);

    void updateEntityFromDomain(Book domain, @MappingTarget BookEntity entity);

    void updateDomainFromEntity(BookEntity entity, @MappingTarget Book domain);

}