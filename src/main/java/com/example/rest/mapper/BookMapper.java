package com.example.rest.mapper;

import com.example.rest.dto.BookIncomingDto;
import com.example.rest.dto.BookToResponseDto;;
import com.example.rest.model.Author;
import com.example.rest.model.Book;
import com.example.rest.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface BookMapper {
    @Mapping(target = "author", ignore = true)
    Book toModel (BookIncomingDto incomingDTO);

    @Mapping(source = "author", target = "author", qualifiedByName = "authorToString")
    @Mapping(source = "genres", target = "genres", qualifiedByName = "genresListToString")
    BookToResponseDto toDto (Book book);

    @Named("authorToString")
    default String authorToString(Author author) {
        return author.getName();
    }

    @Named("genresListToString")
    default List<String> genresListToString (List<Genre> genres) {
        if (genres == null) {
            return null;
        }
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
    }
}
