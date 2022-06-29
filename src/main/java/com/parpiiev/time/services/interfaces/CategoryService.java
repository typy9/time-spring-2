package com.parpiiev.time.services.interfaces;

import com.parpiiev.time.services.BaseService;
import com.parpiiev.time.utils.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService<T> extends BaseService<T> {

    boolean save(T t);

    void updateCategoryNameById(int categoryId, String name);

    boolean delete(int id);

    Page<T> findPaginated(Pageable pageable);

    Optional<T> getByName(String name);
}
