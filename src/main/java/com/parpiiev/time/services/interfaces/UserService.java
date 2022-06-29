package com.parpiiev.time.services.interfaces;

import com.parpiiev.time.services.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface UserService<T> extends BaseService<T> {

    Optional<T> getByLogin(String login);

    boolean save(T t);

    void update(T t);

    boolean delete(int id);

    String getUserRole(T userDTO);

    Page<T> findPaginated(Pageable pageable);
}
