package com.example.rest.controller;


import org.springframework.http.ResponseEntity;


public interface DefaultController<T, R> {

    ResponseEntity <T> get(int id);

    ResponseEntity<String> delete(int id);

    ResponseEntity<?> patch(R dto);

    ResponseEntity<?> add(R dto);

}
