package com.example.rest.controller;

import com.example.rest.dto.*;
import com.example.rest.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/genre")
public class GenreController implements DefaultController <GenreToResponseDto, GenreIncomingDto> {

    GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    @GetMapping(value = "{id}")
    public ResponseEntity <GenreToResponseDto> get(@PathVariable("id") int id) {
        Optional<GenreToResponseDto> genreOptional = genreService.findById(id);
        GenreToResponseDto genreToResponseDTO = genreOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect id"));
        return new ResponseEntity(genreToResponseDTO, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<GenreToResponseDto> genreOptional = genreService.findById(id);
        if (!genreOptional.isPresent()) {
            return ResponseEntity.status(400).body("Incorrect id");
        }
        boolean result = genreService.delete(id);
        if (result) {
            return ResponseEntity.status(200).body("Genre deleted");
        }
        return ResponseEntity.status(500).body("Server error");
    }


    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@RequestBody GenreIncomingDto incomingDto) {
        Optional<GenreToResponseDto> dtoOptional = genreService.findById(incomingDto.getId());
        if (!dtoOptional.isPresent()) {
            return ResponseEntity.status(400).body("Incorrect id");
        }

        GenreToResponseDto genreToResponseDTO = genreService.patch(incomingDto);
        if (genreToResponseDTO == null) {
            return ResponseEntity.status(500).body("Server error");
        }
        return ResponseEntity.status(200).body(genreToResponseDTO);
    }


    @Override
    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> add(@RequestBody GenreIncomingDto dto) {
        GenreToResponseDto genreToResponseDTO = genreService.add(dto);
        if (genreToResponseDTO == null) {
            return ResponseEntity.status(500).body("Server error");
        }
        return ResponseEntity.status(201).body(genreToResponseDTO);
    }
}
