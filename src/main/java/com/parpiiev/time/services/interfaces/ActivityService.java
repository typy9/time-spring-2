package com.parpiiev.time.services.interfaces;

import com.parpiiev.time.services.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface ActivityService<T> extends BaseService<T> {

    Optional<T> getByName(String name);

    boolean save(T t);

    void update(T t);

    boolean delete(int id);

    Page<T> findPaginatedSorted(Pageable pageable, String sortBy);
    Page<T> findPaginated(Pageable pageable);

    List<Optional<T>> getActivityByCategory_Id(int categoryId);

    Page<T> findPaginatedSortByUsers(Pageable pageable);

    Page<T> findPaginatedSortedFiltered(Pageable pageable, String sortBy, String filterByCategory);
}
