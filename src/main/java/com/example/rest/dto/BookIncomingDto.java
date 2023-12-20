package com.example.rest.dto;

import java.util.List;

public class BookIncomingDto {
    private int id;
    private String name;
    private Integer price;
    private Integer authorId;
    private List<Integer> genresId;

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

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAuthor() {
        return authorId;
    }

    public void setAuthor(Integer authorId) {
        this.authorId = authorId;
    }

    public List<Integer> getGenresId() {
        return genresId;
    }

    public void setGenresId(List<Integer> genresId) {
        this.genresId = genresId;
    }
}
