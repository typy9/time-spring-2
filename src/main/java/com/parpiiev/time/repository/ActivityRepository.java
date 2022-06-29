package com.parpiiev.time.repository;

import com.parpiiev.time.model.Activity;
import com.parpiiev.time.utils.projections.ActivityProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer>{


    @Query("select a from Activity a where a.name=:name")
    Optional<Activity> getByName(@Param(value = "name") String name);

    @Modifying @Transactional
    @Query("update Activity a set a.name=:name, a.category.id=:categoryId where a.id=:id")
    void updateActivityById(@Param(value = "id") int id, @Param(value = "name") String name,
                        @Param(value = "categoryId") int categoryId);

    @Query("select a from Activity a where a.category.id=:categoryId")
    List<Optional<Activity>> getActivityByCategoryId(@Param(value = "categoryId") int categoryId);

    @Query("SELECT new com.parpiiev.time.utils.projections.ActivityProjection(a.id, a.name, a.category.id) " +
            "FROM Activity a LEFT JOIN UsersActivity ua " +
            "ON a.id = ua.activity.id " +
            "GROUP BY a.id " +
            "ORDER BY COUNT(ua.user.userId) DESC")
    Page<ActivityProjection> findPaginatedSortByUsers(Pageable pageable);

}
