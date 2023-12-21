package dao;

import com.example.rest.model.Book;
import com.example.rest.model.Genre;
import com.example.rest.repository.AuthorRepository;
import com.example.rest.repository.BookRepository;
import com.example.rest.repository.GenreRepository;
import comon.TestbaseSetup;
import config.PersistenceConfigForTest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static config.PersistenceConfigForTest.mySQLContainer;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigForTest.class, BookRepository.class, GenreRepository.class, AuthorRepository.class})

public class GenreRepositoryTest extends TestbaseSetup {

    BookRepository bookRepository;

    GenreRepository genreRepository;

    AuthorRepository authorRepository;

    @Autowired
    public GenreRepositoryTest(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }
    @BeforeAll
    static void setup() {
        mySQLContainer.start();
    }

    @Test
    void findBooksByGenreIdTest() {
        Genre genre = genreRepository.findById(4).get();
        List<Book> expected = genre.getBooks();
        List<Book> actual = genreRepository.findBooksByGenreId(genre.getId());
        Assert.assertEquals(expected.get(0).toString(), actual.get(0).toString());
    }
}
