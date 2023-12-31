package com.example.rest.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer price;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable (name = "book_genre",
                joinColumns = @JoinColumn (name = "book_id"), inverseJoinColumns = @JoinColumn (name = "genre_id"))
    private List <Genre> genres;


    public Book() {}
    public Book(int id, String name, Integer price, Author author) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.author = author;
    }

    public Book(int id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
