package com.example.rest.service;


import com.example.rest.dto.GenreIncomingDto;
import com.example.rest.dto.GenreToResponseDto;
import com.example.rest.mapper.GenreMapperImpl;
import com.example.rest.model.Book;
import com.example.rest.model.Genre;
import com.example.rest.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService implements SimpleService <GenreIncomingDto, GenreToResponseDto> {

    GenreRepository genreRepository;
    GenreMapperImpl mapper = new GenreMapperImpl();

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<GenreToResponseDto> findById(int id) {
        try {
            Genre genre = genreRepository.findById(id).get();
            GenreToResponseDto genreToResponseDTO = mapper.toDto(genre);
            return Optional.of(genreToResponseDTO);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public boolean delete(int id) {
        try {
            Optional <Genre> genreOptional = genreRepository.findById(id);
            if (genreOptional.isEmpty()) {
                return false;
            }
            genreRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public GenreToResponseDto add(GenreIncomingDto incomingDto)  {
        try {
            Genre genre = mapper.toModel(incomingDto);
            if (incomingDto.getBooksId() != null) {
                genre.setBooks(getBooksByGenreId(incomingDto));
            }
            if (incomingDto.getName() != null) {
                genre.setName(incomingDto.getName());
            } else {
                return null;
            }
            genre = genreRepository.save(genre);
            GenreToResponseDto genreToResponseDTO = mapper.toDto(genre);
            return genreToResponseDTO;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public GenreToResponseDto patch (GenreIncomingDto incomingDto) {
        try {

            Optional<Genre> genreOptional = genreRepository.findById(incomingDto.getId());
            if (!genreOptional.isPresent()) {
                return null;
            }
            Genre genre = genreOptional.get();

            if (incomingDto.getBooksId() != null) {
                genre.setBooks(getBooksByGenreId(incomingDto));
            }

            if (incomingDto.getName() != null) {
                genre.setName(incomingDto.getName());
            }
            Genre genreFromRep = genreRepository.save(genre);
            GenreToResponseDto genreToResponseDto = mapper.toDto(genreFromRep);
            return genreToResponseDto;
        } catch (Exception e) {
            return null;
        }
    }

    private List<Book> getBooksByGenreId (GenreIncomingDto incomingDto) {
        List<Book> books = new ArrayList<>();
        for (int id : incomingDto.getBooksId()) {
            List <Book> bookList = genreRepository.findBooksByGenreId(id);
            books.addAll(bookList);
        }
        return books;
    }
}

