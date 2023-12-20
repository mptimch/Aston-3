package controller;

import com.example.rest.controller.AuthorController;
import com.example.rest.dto.AuthorIncomingDto;
import com.example.rest.dto.AuthorToResponseDto;
import com.example.rest.mapper.AuthorMapperImpl;
import com.example.rest.model.Author;
import com.example.rest.service.AuthorService;
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
public class AuthorControllerTest extends TestbaseSetup {

    @Mock
    private AuthorService service;

    @InjectMocks
    private AuthorController authorController;

    private MockMvc mockMvc;
    AuthorMapperImpl mapper = new AuthorMapperImpl();
    private ObjectMapper objectMapper;
    Author testAuthor = createAuthor();


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getTest() throws Exception {
        AuthorToResponseDto dto = mapper.toDto(author1);
        Optional<AuthorToResponseDto> authorOptional = Optional.of(dto);
        Mockito.when(service.findById(1)).thenReturn(authorOptional);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/author/1"))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Лев Толстой"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0]").value(equalTo("Война и мир")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[1]").value(equalTo("Анна Каренина")));
    }


    @Test
    void getTestException() throws Exception {
        Mockito.when(service.findById(999)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect id"));
        mockMvc.perform(get("/author/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"Incorrect id\"", result.getResolvedException().getMessage()));
        Assert.assertThrows(ResponseStatusException.class, () -> authorController.get(999));
    }


    @Test
    void postTest() throws Exception {
        AuthorIncomingDto authorIncomingDTO = new AuthorIncomingDto();
        authorIncomingDTO.setName("Новый автор");
        authorIncomingDTO.setBooksId(List.of(1, 3));
        AuthorToResponseDto dto = mapper.toDto(testAuthor);
        String authorIncomingDTOJson = objectMapper.writeValueAsString(authorIncomingDTO);

        Mockito.when(service.add(Mockito.any(AuthorIncomingDto.class))).thenReturn(dto);
        mockMvc.perform(post("/author/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorIncomingDTOJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equalTo("Новый автор")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0]").value(equalTo("Война и мир")));
    }


    @Test
    void patchTest() throws Exception {
        testAuthor.setName("Новый измененный автор");
        AuthorIncomingDto dto = new AuthorIncomingDto();
        dto.setName(testAuthor.getName());
        dto.setId(testAuthor.getId());
        dto.setBooksId(List.of(1, 3));
        AuthorToResponseDto authorToResponseDto = mapper.toDto(testAuthor);

        String authorIncomingDTOJson = objectMapper.writeValueAsString(dto);
        Mockito.when(service.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(authorToResponseDto));
        Mockito.when(service.patch(Mockito.any(AuthorIncomingDto.class))).thenReturn(authorToResponseDto);

        mockMvc.perform(patch("/author/" + testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorIncomingDTOJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equalTo("Новый измененный автор")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books[0]").value(equalTo("Война и мир")));
    }


    @Test
    void deleteTest() throws Exception {
        Author author = createAuthor();
        AuthorToResponseDto dto = mapper.toDto(author);
        Mockito.when(service.findById(4)).thenReturn(Optional.of(dto));
        Mockito.when(service.delete(4)).thenReturn(true);

        Assert.assertEquals(authorController.delete(4), ResponseEntity.status(200).body("Author deleted"));
        Assert.assertEquals(authorController.delete(2), ResponseEntity.status(400).body("Incorrect id"));

    }


    private Author createAuthor() {
        Author author = new Author();
        author.setName("Новый автор");
        author.setBooks(List.of(book1, book3));
        return author;
    }
}
