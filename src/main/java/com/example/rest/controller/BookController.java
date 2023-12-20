package com.example.rest.controller;


import com.example.rest.dto.BookIncomingDto;
import com.example.rest.dto.BookToResponseDto;
import com.example.rest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController implements DefaultController<BookToResponseDto, BookIncomingDto> {

    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity <BookToResponseDto> get(@PathVariable("id") int id) {
        Optional<BookToResponseDto> bookOptional = bookService.findById(id);
        BookToResponseDto bookToResponseDTO = bookOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect id"));
        return new  ResponseEntity<>(bookToResponseDTO, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<BookToResponseDto> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return ResponseEntity.status(400).body("Incorrect id");
        }
        boolean result = bookService.delete(id);
        if (result) {
            return ResponseEntity.status(200).body("Book deleted");
        }
        return ResponseEntity.status(500).body("Server error");
    }


    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@RequestBody BookIncomingDto incomingDto) {
        Optional<BookToResponseDto> dtoOptional = bookService.findById(incomingDto.getId());
        if (dtoOptional.isEmpty()) {
            return ResponseEntity.status(400).body("Incorrect id");
        }

        BookToResponseDto bookToResponseDTO = bookService.patch(incomingDto);
        if (bookToResponseDTO == null) {
            return ResponseEntity.status(500).body("Server error");
        }
        return ResponseEntity.status(200).body(bookToResponseDTO);
    }


    @Override
    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> add(@RequestBody BookIncomingDto dto) {
        BookToResponseDto bookToResponseDTO = bookService.add(dto);
        if (bookToResponseDTO == null) {
            return ResponseEntity.status(500).body("Server error");
        }
        return ResponseEntity.status(201).body(bookToResponseDTO);
    }
}

