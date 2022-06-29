package com.parpiiev.time.services.interfaces;

import com.parpiiev.time.services.BaseService;
import com.parpiiev.time.utils.dto.ActivityRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ActivityRequestService<T> extends BaseService<T> {

    Optional<T> getByUserIdActivityId(int userId, int activityId);

    boolean save(T t);

    void update(T t);

    boolean delete(int id);

    List<Optional<T>> getAllByUserId(int id);

    Page<ActivityRequestDTO> findPaginated(Pageable pageable);
}
