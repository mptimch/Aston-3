package com.example.rest.service;

import com.example.rest.dto.AuthorIncomingDto;
import com.example.rest.dto.AuthorToResponseDto;
import com.example.rest.mapper.AuthorMapperImpl;
import com.example.rest.model.Author;
import com.example.rest.model.Book;
import com.example.rest.repository.AuthorRepository;
import com.example.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements SimpleService<AuthorIncomingDto, AuthorToResponseDto> {

    AuthorRepository authorRepository;
    BookRepository bookRepository;

    AuthorMapperImpl mapper = new AuthorMapperImpl();

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public Optional<AuthorToResponseDto> findById(int id) {
        try {
            Author author = authorRepository.findById(id).get();
            AuthorToResponseDto authorToResponseDTO = mapper.toDto(author);
            return Optional.of(authorToResponseDTO);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public boolean delete(int id) {
        try {
            Optional <Author> author = authorRepository.findById(id);
            if (author.isEmpty()) {
                return false;
            }
            authorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public AuthorToResponseDto add(AuthorIncomingDto incomingDto) {
        try {
            Author author = mapper.toModel(incomingDto);
            if (incomingDto.getBooksId() != null) {
                List <Book> books = getBooksFromIds(incomingDto);
                author.setBooks(getBooksFromIds(incomingDto));
            }
            if (incomingDto.getName() != null) {
                author.setName(incomingDto.getName());
            } else {
                return null;
            }
            author = authorRepository.save(author);
            AuthorToResponseDto authorToResponseDTO = mapper.toDto(author);
            return authorToResponseDTO;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public AuthorToResponseDto patch(AuthorIncomingDto incomingDto) {
        try {
            Optional<Author> authorOptional = authorRepository.findById(incomingDto.getId());
            if (!authorOptional.isPresent()) {
                return null;
            }
            Author author = authorOptional.get();

            if (incomingDto.getBooksId() != null) {
                author.setBooks(getBooksFromIds(incomingDto));
            } else {
                author.setBooks(author.getBooks());
            }

            if (incomingDto.getName() != null) {
                author.setName(incomingDto.getName());
            } else {
                author.setName(author.getName());
            }
            author = authorRepository.save(author);
            AuthorToResponseDto authorToResponseDTO = mapper.toDto(author);
            return authorToResponseDTO;
        } catch (Exception e) {
            return null;
        }
    }

    private List<Book> getBooksFromIds(AuthorIncomingDto incomingDto) {
        List<Book> bookList = new ArrayList<>();
        for (int id : incomingDto.getBooksId()) {
            Book book = bookRepository.findById(id).get();
            bookList.add(book);
        }
        return bookList;
    }
}
