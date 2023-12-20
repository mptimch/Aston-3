package com.example.rest.mapper;

import com.example.rest.dto.AuthorIncomingDto;
import com.example.rest.dto.AuthorToResponseDto;
import com.example.rest.model.Author;
import com.example.rest.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface AuthorMapper {

    Author toModel (AuthorIncomingDto incomingDTO);

    @Mapping(source = "books", target = "books", qualifiedByName = "bookListToStringList")
    AuthorToResponseDto toDto (Author author);

    @Named("bookListToStringList")
    default List<String> bookListToStringList(List<Book> books) {
        if (books == null) {
            return null;
        }
        return books.stream()
                .map(Book::getName)
                .collect(Collectors.toList());
    }
}
