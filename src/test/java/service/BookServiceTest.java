package service;

import com.example.rest.dto.BookIncomingDto;
import com.example.rest.dto.BookToResponseDto;
import com.example.rest.mapper.BookMapperImpl;
import com.example.rest.model.Book;
import com.example.rest.repository.AuthorRepository;
import com.example.rest.repository.BookRepository;
import com.example.rest.service.BookService;
import comon.TestbaseSetup;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest extends TestbaseSetup {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService service;

    BookMapperImpl mapper = new BookMapperImpl();
    Book testBook = createNewBook();


    @Test
    void findByIdTest() throws Exception {
        BookToResponseDto bookToResponseDto = mapper.toDto(book1);
        Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));
        assertAll(
                () -> assertEquals(book1.getName(), service.findById(book1.getId()).get().getName()),
                () -> assertEquals(bookToResponseDto.getAuthor(), service.findById(book1.getId()).get().getAuthor())
        );
    }


    @Test
    void addTest() {
        BookIncomingDto incomingDto = new BookIncomingDto();
        incomingDto.setId(3);
        incomingDto.setName("Новая книга");
        incomingDto.setPrice(500);
        incomingDto.setAuthor(1);
        incomingDto.setGenresId(List.of(1, 3));
        BookToResponseDto responseDto = mapper.toDto(testBook);

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(testBook);
        Mockito.when(authorRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(author1));
        Mockito.when(bookRepository.findGenresByBookId(1)).thenReturn(List.of(genre1, genre3));
        assertAll(
                () -> assertEquals(responseDto.getName(), service.add(incomingDto).getName()),
                () -> assertEquals(responseDto.getAuthor(), service.add(incomingDto).getAuthor()),
                () -> assertEquals(responseDto.getGenres(), service.add(incomingDto).getGenres())
        );
    }


    @Test
    void patchTest() {
        BookIncomingDto incomingDto = new BookIncomingDto();
        incomingDto.setId(3);
        incomingDto.setName("Измененная книга");
        BookToResponseDto responseDto = mapper.toDto(testBook);

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(testBook);
        Mockito.when(bookRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(testBook));
        assertAll(
                () -> assertEquals(incomingDto.getName(), service.patch(incomingDto).getName()),
                () -> assertEquals(responseDto.getGenres(), service.patch(incomingDto).getGenres())
        );
    }


    @Test
    void deleteTest() {
        Mockito.doNothing().when(bookRepository).deleteById(Mockito.any(Integer.class));
        Mockito.when(bookRepository.findById(2)).thenReturn(Optional.of(testBook));
        Assert.assertEquals(service.delete(2), true);
        Assert.assertEquals(service.delete(99), false);
    }

    private Book createNewBook() {
        Book book = new Book();
        book.setName("Новая книга");
        book.setPrice(2500);
        book.setAuthor(author1);
        book.setGenres(List.of(genre1, genre2));
        return book;
    }
}