package com.parpiiev.time.repository;

import com.parpiiev.time.model.ActivityRequest;
import com.parpiiev.time.model.Status;
import com.parpiiev.time.model.UsersActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRequestRepository extends JpaRepository<ActivityRequest, Integer> {

    @Query("select ar from ActivityRequest ar where ar.user.userId =:userId and ar.activity.id=:activityId")
    Optional<ActivityRequest> findByUserIdActivityId(@Param(value = "userId") int userId,
                                                   @Param(value = "activityId") int activityId);

    @Query("select a from ActivityRequest a where a.user.userId=:userId")
    List<Optional<ActivityRequest>> findAllByUserId(@Param(value = "userId") int userId);

    @Modifying
    @Transactional
    @Query("update ActivityRequest a set a.status=:status where a.request_id=:requestId")
    void updateRequestById(@Param(value = "requestId") int requestId, @Param(value = "status") Status status);
}
