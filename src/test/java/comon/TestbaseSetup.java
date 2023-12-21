package comon;

import com.example.rest.model.Book;
import com.example.rest.model.Genre;
import com.example.rest.repository.AuthorRepository;
import com.example.rest.repository.BookRepository;
import com.example.rest.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TestbaseSetup extends TestSetup {


    AuthorRepository authorRepository;
    BookRepository bookRepository;
    GenreRepository genreRepository;

    public void createBases() {

        authorRepository.save(author1);
        authorRepository.save(author2);

        List<Book> books = List.of(book1, book2, book3);
        for (Book book : books) {
            bookRepository.save(book);
        }

        List<Genre> genres = List.of(genre1, genre2, genre3);
        for (Genre genre4 : genres) {
            genreRepository.save(genre4);
        }

        for (Book book : books) {
            List<Genre> genreList = book.getGenres();
            List<Integer> genresId = genreList.stream().map(Genre::getId).collect(Collectors.toList());
            bookRepository.save(book);
        }
        for (Genre genre : genres) {
            List<Book> bookList = genre.getBooks();
            List<Integer> booksId = bookList.stream().map(Book::getId).collect(Collectors.toList());
            genreRepository.save(genre);
        }
    }
}
