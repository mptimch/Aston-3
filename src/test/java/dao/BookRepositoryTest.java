package dao;

import com.example.rest.model.Author;
import com.example.rest.model.Book;
import com.example.rest.model.Genre;
import com.example.rest.repository.AuthorRepository;
import com.example.rest.repository.BookRepository;
import com.example.rest.repository.GenreRepository;
import comon.TestbaseSetup;
import config.PersistenceConfigForTest;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static config.PersistenceConfigForTest.mySQLContainer;

;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigForTest.class, BookRepository.class, GenreRepository.class, AuthorRepository.class})
public class BookRepositoryTest extends TestbaseSetup {

    BookRepository bookRepository;

    GenreRepository genreRepository;

    AuthorRepository authorRepository;

    @Autowired
    public BookRepositoryTest(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @BeforeAll
    static void setup() {
        mySQLContainer.start();
    }

    @AfterAll
    static void close() {
        mySQLContainer.stop();
    }


    @Test
    void findGenresByBookIdTest() {
        Book book = bookRepository.findById(10).get();
        List <Genre> expected = book.getGenres();
        List <Genre> actual = bookRepository.findGenresByBookId(book.getId());
        Assert.assertEquals(expected.get(0).toString(), actual.get(0).toString());
    }

    @Test
    void getBooksByAuthorIdTest() {
        Author author = authorRepository.findById(10).get();
        List<Book> expected = author.getBooks();
        List<Book> actual = bookRepository.getBooksByAuthorId(author.getId());
        Assert.assertEquals(expected.get(0).toString(), actual.get(0).toString());
    }
}
