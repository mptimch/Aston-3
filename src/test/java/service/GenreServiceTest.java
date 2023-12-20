package service;

import com.example.rest.dto.GenreIncomingDto;
import com.example.rest.dto.GenreToResponseDto;
import com.example.rest.mapper.GenreMapperImpl;
import com.example.rest.model.Genre;
import com.example.rest.repository.BookRepository;
import com.example.rest.repository.GenreRepository;
import com.example.rest.service.GenreService;
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
public class GenreServiceTest extends TestbaseSetup {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private GenreService service;

    GenreMapperImpl mapper = new GenreMapperImpl();
    Genre testGenre = createNewGenre();


    @Test
    void findByIdTest() throws Exception {
        GenreToResponseDto genreToResponseDto = mapper.toDto(genre3);
        Mockito.when(genreRepository.findById(genre3.getId())).thenReturn(Optional.of(genre3));
        assertAll(
                () -> assertEquals(genre3.getName(), service.findById(genre3.getId()).get().getName()),
                () -> assertEquals(genreToResponseDto.getBooks(), service.findById(genre3.getId()).get().getBooks())
        );
    }


    @Test
    void addTest() {
        GenreIncomingDto incomingDto = new GenreIncomingDto();
        incomingDto.setId(3);
        incomingDto.setName("Новый жанр");
        incomingDto.setBooks(List.of(1, 3));
        GenreToResponseDto responseDto = mapper.toDto(testGenre);

        Mockito.when(genreRepository.save(Mockito.any(Genre.class))).thenReturn(testGenre);
        Mockito.when(genreRepository.findBooksByGenreId(1)).thenReturn(List.of(book1, book3));
        assertAll(
                () -> assertEquals(responseDto.getName(), service.add(incomingDto).getName()),
                () -> assertEquals(responseDto.getBooks(), service.add(incomingDto).getBooks())
        );
    }


    @Test
    void patchTest() {
        GenreIncomingDto incomingDto = new GenreIncomingDto();
        incomingDto.setId(3);
        incomingDto.setName("Измененный жанр");
        GenreToResponseDto responseDto = mapper.toDto(testGenre);

        Mockito.when(genreRepository.save(Mockito.any(Genre.class))).thenReturn(testGenre);
        Mockito.when(genreRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(testGenre));
        assertAll(
                () -> assertEquals(incomingDto.getName(), service.patch(incomingDto).getName()),
                () -> assertEquals(responseDto.getBooks(), service.patch(incomingDto).getBooks())
        );
    }


    @Test
    void deleteTest() {
        Mockito.doNothing().when(genreRepository).deleteById(Mockito.any(Integer.class));
        Mockito.when(genreRepository.findById(2)).thenReturn(Optional.of(testGenre));
        Assert.assertEquals(service.delete(2), true);
        Assert.assertEquals(service.delete(99), false);
    }


    private Genre createNewGenre() {
        Genre genre = new Genre();
        genre.setName("Новый жанр");
        genre.setBooks(List.of(book1, book3));
        return genre;
    }
}