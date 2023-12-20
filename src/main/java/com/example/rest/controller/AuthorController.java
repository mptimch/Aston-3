package com.example.rest.controller;

import com.example.rest.dto.AuthorIncomingDto;
import com.example.rest.dto.AuthorToResponseDto;
import com.example.rest.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController implements DefaultController<AuthorToResponseDto, AuthorIncomingDto> {

    AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity <AuthorToResponseDto> get(@PathVariable int id) {
        Optional<AuthorToResponseDto> authorOptional = authorService.findById(id);
        AuthorToResponseDto authorToResponseDTO = authorOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect id"));
        return new ResponseEntity<>(authorToResponseDTO, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<AuthorToResponseDto> authorOptional = authorService.findById(id);
        if (!authorOptional.isPresent()) {
            return ResponseEntity.status(400).body("Incorrect id");
        }
        boolean result = authorService.delete(id);
        if (result) {
            return ResponseEntity.status(200).body("Author deleted");
        }
        return ResponseEntity.status(500).body("Server error");
    }


    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@RequestBody AuthorIncomingDto incomingDtodto) {
        Optional<AuthorToResponseDto> dtoOptional = authorService.findById(incomingDtodto.getId());
        if (!dtoOptional.isPresent()) {
            return ResponseEntity.status(400).body("Incorrect id");
        }
        AuthorToResponseDto authorToResponseDTO = authorService.patch(incomingDtodto);
        if (authorToResponseDTO == null) {
            return ResponseEntity.status(500).body("Server error");
        }
        return ResponseEntity.status(200).body(authorToResponseDTO);
    }


    @Override
    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> add (@RequestBody AuthorIncomingDto dto) {
        AuthorToResponseDto authorToResponseDTO = authorService.add(dto);
        if (authorToResponseDTO == null) {
            return ResponseEntity.status(500).body("Please insert correct name");
        }
        return ResponseEntity.status(201).body(authorToResponseDTO);
    }
}

