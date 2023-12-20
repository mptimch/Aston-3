package com.example.rest.repository;

import com.example.rest.model.Book;
import com.example.rest.model.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    List<Book> getBooksByAuthorId(int id);

    @Query("SELECT g FROM Book b JOIN b.genres g WHERE b.id = :bookId")
    List<Genre> findGenresByBookId(@Param("bookId") int bookId);
}
