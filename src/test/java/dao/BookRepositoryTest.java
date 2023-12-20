package dao;

import com.example.rest.model.Genre;
import com.example.rest.repository.BookRepository;
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

;import static config.PersistenceConfigForTest.mySQLContainer;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigForTest.class, BookRepository.class})
public class BookRepositoryTest extends TestbaseSetup {

    @Autowired
    BookRepository bookRepository;


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
        List<Genre> expected = book1.getGenres();
        List<Genre> actual = bookRepository.findGenresByBookId(book1.getId());
        Assert.assertEquals(expected, actual);
    }

}
