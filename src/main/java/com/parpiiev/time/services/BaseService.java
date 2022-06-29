package com.parpiiev.time.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    Optional<T> getById(int id);

    List<T> getAll();

}
