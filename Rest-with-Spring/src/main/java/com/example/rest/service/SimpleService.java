package com.example.rest.service;

import java.util.Optional;

public interface SimpleService <T, R> {

    Optional <R> findById(int id);

    boolean delete (int id);

    R add(T t);

    R patch (T t);
}
