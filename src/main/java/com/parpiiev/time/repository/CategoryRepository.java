package com.parpiiev.time.repository;

import com.parpiiev.time.model.Category;
import com.parpiiev.time.model.User;
import org.apache.el.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Modifying @Transactional
    @Query("update Category c set c.name=:name where c.id=:id")
    void updateCategoryNameById(@Param(value = "id") int id, @Param(value = "name") String name);

    @Query("select c from Category c where c.name=:name")
    Optional<Category> findByName(String name);
}
