package controller;

import com.example.rest.controller.GenreController;
import com.example.rest.dto.GenreIncomingDto;
import com.example.rest.dto.GenreToResponseDto;
import com.example.rest.mapper.GenreMapperImpl;
import com.example.rest.model.Genre;
import com.example.rest.service.GenreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import comon.TestbaseSetup;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class GenreControllerTest extends TestbaseSetup {

    @Mock
    private GenreService service;

    @InjectMocks
    private GenreController genreController;

    private MockMvc mockMvc;
    GenreMapperImpl mapper = new GenreMapperImpl();
    private ObjectMapper objectMapper;
    Genre testGenre = createGenre();


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void getTest() throws Exception {
        GenreToResponseDto dto = mapper.toDto(genre1);
        Optional<GenreToResponseDto> genreOptional = Optional.of(dto);
        Mockito.when(service.findById(1)).thenReturn(genreOptional);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/genre/1"))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Классическая литература"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0]").value(equalTo("Война и мир")));
    }


    @Test
    void getTestException() throws Exception {
        Mockito.when(service.findById(999)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect id"));
        mockMvc.perform(get("/genre/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"Incorrect id\"", result.getResolvedException().getMessage()));
        Assert.assertThrows(ResponseStatusException.class, () -> genreController.get(999));
    }


    @Test
    void postTest() throws Exception {
        GenreIncomingDto genreIncomingDto = new GenreIncomingDto();
        genreIncomingDto.setName("Новый жанр");
        GenreToResponseDto dto = mapper.toDto(testGenre);
        String genreIncomingDTOJson = objectMapper.writeValueAsString(genreIncomingDto);

        Mockito.when(service.add(Mockito.any(GenreIncomingDto.class))).thenReturn(dto);
        mockMvc.perform(post("/genre/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(genreIncomingDTOJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equalTo("Новый жанр")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1]").value(equalTo("Анна Каренина")));
    }


    @Test
    void patchTest() throws Exception {
        testGenre.setName("Новая измененная книга");
        GenreToResponseDto dtoToResponse = mapper.toDto(testGenre);
        GenreIncomingDto incomingDto = new GenreIncomingDto();
        incomingDto.setName("Новая измененная книга");
        incomingDto.setId(4);
        incomingDto.setBooks(List.of(1, 3));
        Optional<GenreToResponseDto> optional = Optional.of(dtoToResponse);
        String genreIncomingDTOJson = objectMapper.writeValueAsString(incomingDto);
        Mockito.when(service.findById(4)).thenReturn(optional);
        Mockito.when(service.patch(Mockito.any(GenreIncomingDto.class))).thenReturn(dtoToResponse);

        mockMvc.perform(patch("/genre/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(genreIncomingDTOJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equalTo("Новая измененная книга")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1]").value(equalTo("Анна Каренина")));
    }


    @Test
    void deleteTest() throws Exception {
        Genre genre = createGenre();
        GenreToResponseDto dto = mapper.toDto(genre);
        Mockito.when(service.findById(4)).thenReturn(Optional.of(dto));
        Mockito.when(service.delete(4)).thenReturn(true);

        Assert.assertEquals(genreController.delete(4), ResponseEntity.status(200).body("Genre deleted"));
        Assert.assertEquals(genreController.delete(2), ResponseEntity.status(400).body("Incorrect id"));
    }


    private Genre createGenre() {
        Genre genre = new Genre();
        genre.setName("Новый жанр");
        genre.setBooks(List.of(book1, book3));
        return genre;
    }
}



