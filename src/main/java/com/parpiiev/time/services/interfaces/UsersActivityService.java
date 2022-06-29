package com.parpiiev.time.services.interfaces;

import com.parpiiev.time.services.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsersActivityService<T> extends BaseService<T> {

    Optional<T> getByUserIdActivityId(int userId, int activityId);
    boolean save(T t);

    void update(T t);

    boolean delete(int id);

    List<T> getAllById(int id);

    Page<T> findPaginated(Pageable pageable, int userId);

    Page<T> findPaginatedFull(Pageable pageable);

}
