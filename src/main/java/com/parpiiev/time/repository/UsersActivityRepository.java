package com.parpiiev.time.repository;

import com.parpiiev.time.model.User;
import com.parpiiev.time.model.UsersActivity;
import org.apache.el.stream.Stream;
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
public interface UsersActivityRepository extends JpaRepository<UsersActivity, Integer> {

    @Query("select u from UsersActivity u where u.user.userId=:userId")
    List<UsersActivity> findAllUserRecordsById(@Param(value = "userId") int userId);

    @Modifying
    @Transactional
    @Query("update UsersActivity u set u.time=:time where u.id=:recordId")
    void updateRecordById(@Param(value = "recordId") int recordId, @Param(value = "time") int time);

    @Query("select ua from UsersActivity ua where ua.user.userId =:userId")
    Page<UsersActivity> findPaginatedByUserId(Pageable pageable, int userId);


    @Query("select ua from UsersActivity ua where ua.user.userId =:userId and ua.activity.id=:activityId")
    Optional<UsersActivity> findByUserIdActivityId(@Param(value = "userId") int userId,
                                                   @Param(value = "activityId") int activityId);
}
