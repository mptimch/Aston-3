package service;

import com.example.rest.dto.AuthorIncomingDto;
import com.example.rest.dto.AuthorToResponseDto;
import com.example.rest.mapper.AuthorMapperImpl;
import com.example.rest.model.Author;
import com.example.rest.repository.AuthorRepository;
import com.example.rest.repository.BookRepository;
import com.example.rest.service.AuthorService;
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
public class AuthorServiceTest extends TestbaseSetup {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService service;

    AuthorMapperImpl mapper = new AuthorMapperImpl();
    Author testAuthor = createNewAuthor();


    @Test
    void findByIdTest() throws Exception {
        AuthorToResponseDto authorToResponseDto = mapper.toDto(author1);
        Mockito.when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author1));
        assertAll(
                () -> assertEquals(author1.getName(), service.findById(author1.getId()).get().getName()),
                () -> assertEquals(authorToResponseDto.getBooks(), service.findById(author1.getId()).get().getBooks())
        );
    }


    @Test
    void addTest() {
        AuthorIncomingDto incomingDto = new AuthorIncomingDto();
        incomingDto.setId(3);
        incomingDto.setName("Аффтор жжёт");
        incomingDto.setBooksId(List.of(2));

        AuthorToResponseDto responseDto = mapper.toDto(testAuthor);

        Mockito.when(authorRepository.save(Mockito.any(Author.class))).thenReturn(testAuthor);
        Mockito.when(bookRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(book2));
        assertAll(
                () -> assertEquals(responseDto.getName(), service.add(incomingDto).getName()),
                () -> assertEquals(responseDto.getBooks(), service.add(incomingDto).getBooks())
        );
    }


    @Test
    void patchTest() {
        AuthorIncomingDto incomingDto = new AuthorIncomingDto();
        incomingDto.setId(3);
        incomingDto.setName("Измененный автор");

        AuthorToResponseDto responseDto = mapper.toDto(testAuthor);

        Mockito.when(authorRepository.save(Mockito.any(Author.class))).thenReturn(testAuthor);
        Mockito.when(authorRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(testAuthor));
        assertAll(
                () -> assertEquals(incomingDto.getName(), service.patch(incomingDto).getName()),
                () -> assertEquals(responseDto.getBooks(), service.patch(incomingDto).getBooks())
        );
    }


    @Test
    void deleteTest() {
        Mockito.doNothing().when(authorRepository).deleteById(Mockito.any(Integer.class));
        Mockito.when(authorRepository.findById(2)).thenReturn(Optional.of(testAuthor));
        Assert.assertEquals(service.delete(2), true);
        Assert.assertEquals(service.delete(99), false);
    }


    private Author createNewAuthor() {
        Author author = new Author();
        author.setName("Аффтор жжёт");
        author.setBooks(List.of(book2));
        return author;
    }
}
