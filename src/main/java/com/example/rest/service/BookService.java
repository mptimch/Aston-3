package com.example.rest.service;

import com.example.rest.dto.BookIncomingDto;
import com.example.rest.dto.BookToResponseDto;
import com.example.rest.mapper.BookMapperImpl;
import com.example.rest.model.Author;
import com.example.rest.model.Book;
import com.example.rest.model.Genre;
import com.example.rest.repository.AuthorRepository;
import com.example.rest.repository.BookRepository;
import com.example.rest.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements SimpleService <BookIncomingDto, BookToResponseDto>{

    BookRepository bookRepository;
    AuthorRepository authorRepository;
    GenreRepository genreRepository;
    BookMapperImpl mapper = new BookMapperImpl();

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }


    @Override
    public Optional<BookToResponseDto> findById(int id) {
        try {
            Book book = bookRepository.findById(id).get();
            BookToResponseDto bookToResponseDTO = mapper.toDto(book);
            return Optional.of(bookToResponseDTO);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public boolean delete(int id) {
        try {
            Optional <Book> bookOptional = bookRepository.findById(id);
            if (bookOptional.isEmpty()) {
                return false;
            }
            bookRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public BookToResponseDto add(BookIncomingDto incomingDto)  {
        try {
            if (incomingDto.getName() == null || incomingDto.getPrice() == null ) {
                return null;
            }

            Book book = new Book();
            book.setName(incomingDto.getName());
            book.setPrice(incomingDto.getPrice());
           setAuthorAndGenresToBook(book, incomingDto);
            Book bookFromRep = bookRepository.save(book);
            BookToResponseDto bookToResponseDto = mapper.toDto(bookFromRep);
            return bookToResponseDto;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public BookToResponseDto patch(BookIncomingDto incomingDto) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(incomingDto.getId());
            if (!bookOptional.isPresent()) {
                return null;
            }
            Book book = bookOptional.get();
            setAuthorAndGenresToBook(book, incomingDto);
            if (incomingDto.getName() != null) {
                book.setName(incomingDto.getName());
            }
            if (incomingDto.getPrice() != null) {
                book.setPrice(incomingDto.getPrice());
            }

            book = bookRepository.save(book);
            BookToResponseDto bookToResponseDto = mapper.toDto(book);
            return bookToResponseDto;
        } catch (Exception e) {
            return null;
        }
    }


    private List<Genre> getGenresFromBookId(BookIncomingDto incomingDto) {
        List<Genre> genres = new ArrayList<>();
        for (int id : incomingDto.getGenresId()) {
            List <Genre> genreList = bookRepository.findGenresByBookId(id);
            genres.addAll(genreList);
        }
        return genres;
    }

    private void setAuthorAndGenresToBook (Book book, BookIncomingDto incomingDto) throws IllegalArgumentException {
        if (incomingDto.getAuthor() != null) {
            Optional<Author> authorOptional = authorRepository.findById(incomingDto.getId());
            if (!authorOptional.isPresent()) {
                throw new IllegalArgumentException();
            }
            book.setAuthor(authorOptional.get());
        } else {
            book.setAuthor(book.getAuthor());
        }

        if (incomingDto.getGenresId() != null) {
            List <Genre> genres = getGenresFromBookId(incomingDto);
            book.setGenres(genres);
        } else {
            book.setGenres(book.getGenres());
        }
    }
}
