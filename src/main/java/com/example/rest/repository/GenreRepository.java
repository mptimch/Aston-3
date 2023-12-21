package com.example.rest.repository;

import com.example.rest.model.Book;
import com.example.rest.model.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {

    @Query("SELECT b FROM Genre g JOIN g.books b WHERE g.id = :genreId")
    List<Book> findBooksByGenreId(@Param("genreId") int genreId);
}
