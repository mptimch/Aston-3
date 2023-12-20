package controller;

import com.example.rest.controller.BookController;
import com.example.rest.dto.BookIncomingDto;
import com.example.rest.dto.BookToResponseDto;
import com.example.rest.mapper.BookMapperImpl;
import com.example.rest.model.Book;
import com.example.rest.service.BookService;
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
public class BookControllerTest extends TestbaseSetup {

    @Mock
    private BookService service;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;
    BookMapperImpl mapper = new BookMapperImpl();
    private ObjectMapper objectMapper;
    Book testBook = createBook();


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void getTest() throws Exception {
        BookToResponseDto dto = mapper.toDto(book1);
        Optional<BookToResponseDto> bookOptional = Optional.of(dto);
        Mockito.when(service.findById(1)).thenReturn(bookOptional);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/1"))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Война и мир"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Лев Толстой"));
    }


    @Test
    void getTestException() throws Exception {
        Mockito.when(service.findById(999)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect id"));
        mockMvc.perform(get("/book/999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals("404 NOT_FOUND \"Incorrect id\"", result.getResolvedException().getMessage()));
        Assert.assertThrows(ResponseStatusException.class, () -> bookController.get(999));
    }


    @Test
    void postTest() throws Exception {
        BookIncomingDto bookIncomingDto = new BookIncomingDto();
        bookIncomingDto.setName("Новая книга");
        bookIncomingDto.setAuthor(1);
        BookToResponseDto dto = mapper.toDto(testBook);
        String bookIncomingDTOJson = objectMapper.writeValueAsString(bookIncomingDto);

        Mockito.when(service.add(Mockito.any(BookIncomingDto.class))).thenReturn(dto);
        mockMvc.perform(post("/book/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookIncomingDTOJson))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equalTo("Новая книга")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genres[1]").value(equalTo("Роман")));
    }


    @Test
    void patchTest() throws Exception {
        testBook.setName("Новая измененная книга");
        testBook.setPrice(2500);
        BookToResponseDto dtoToResponse = mapper.toDto(testBook);
        BookIncomingDto incomingDto = new BookIncomingDto();
        incomingDto.setAuthor(testBook.getAuthor().getId());
        incomingDto.setName("Новая измененная книга");
        incomingDto.setId(4);
        incomingDto.setGenresId(List.of(1, 3));
        Optional<BookToResponseDto> optional = Optional.of(dtoToResponse);
        String bookIncomingDTOJson = objectMapper.writeValueAsString(incomingDto);
        Mockito.when(service.findById(4)).thenReturn(optional);
        Mockito.when(service.patch(Mockito.any(BookIncomingDto.class))).thenReturn(dtoToResponse);

        mockMvc.perform(patch("/book/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookIncomingDTOJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equalTo("Новая измененная книга")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genres[1]").value(equalTo("Роман")));
    }


    @Test
    void deleteTest() throws Exception {
        Book book = createBook();
        BookToResponseDto dto = mapper.toDto(book);
        Mockito.when(service.findById(4)).thenReturn(Optional.of(dto));
        Mockito.when(service.delete(4)).thenReturn(true);

        Assert.assertEquals(bookController.delete(4), ResponseEntity.status(200).body("Book deleted"));
        Assert.assertEquals(bookController.delete(2), ResponseEntity.status(400).body("Incorrect id"));

    }


    private Book createBook() {
        Book book = new Book();
        book.setName("Новая книга");
        book.setPrice(2500);
        book.setAuthor(author1);
        book.setGenres(List.of(genre1, genre2));
        return book;
    }
}
