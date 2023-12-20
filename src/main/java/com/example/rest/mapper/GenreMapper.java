package com.example.rest.mapper;

import com.example.rest.dto.GenreIncomingDto;
import com.example.rest.dto.GenreToResponseDto;
import com.example.rest.model.Book;
import com.example.rest.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface GenreMapper {
    @Mapping(target = "books", ignore = true)
    Genre toModel (GenreIncomingDto incomingDTO);

    @Mapping(source = "books", target = "books", qualifiedByName = "booksListToString")
    GenreToResponseDto toDto (Genre genre);

    @Named("booksListToString")
    default List<String> booksListToString (List<Book> books) {
        if (books == null) {
            return null;
        }
        return books.stream()
                .map(Book::getName)
                .collect(Collectors.toList());
    }
}
