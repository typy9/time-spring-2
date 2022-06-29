package com.parpiiev.time.services.implementations;

import com.parpiiev.time.exceptions.activity.ActivityAlreadyExistsException;
import com.parpiiev.time.model.Activity;
import com.parpiiev.time.exceptions.user.InvalidUserException;
import com.parpiiev.time.model.User;
import com.parpiiev.time.utils.projections.ActivityProjectionMapper;
import com.parpiiev.time.repository.ActivityRepository;
import com.parpiiev.time.services.interfaces.ActivityService;
import com.parpiiev.time.utils.dto.ActivityDTO;
import com.parpiiev.time.utils.dto.mappers.DtoMapper;
import com.parpiiev.time.utils.validators.PatternMatcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Activity table
 */
@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService<ActivityDTO> {

    private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepository;
    private final DtoMapper<ActivityDTO, Activity> dtoMapper;
    private final ActivityProjectionMapper projectionMapper;


    @Override
    public Optional<ActivityDTO> getById(int id) {

        if (id <= 0) {return Optional.empty();}
        return activityRepository.findById(id).map(dtoMapper::mapToDto);
    }

    @Override
    public List<ActivityDTO> getAll() {
        return activityRepository.findAll().stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ActivityDTO> getByName(String name) {

        if (!PatternMatcher.validateName(name)) {return Optional.empty();}
        return activityRepository.getByName(name).map(dtoMapper::mapToDto);
    }

    @Override
    public boolean save(ActivityDTO activityDto) {
        boolean flag;
        if (activityDto.getName() == null ) {
            log.error("Invalid input data");
            throw new InvalidUserException("Activity input data is not valid");
        }
        Activity activity = getActivity(activityDto);


        if (getByName(activity.getName()).isPresent()) {
            throw new ActivityAlreadyExistsException();
        } else {
            flag = true;
            activityRepository.save(activity);
        }
        return flag;
    }

    @Override
    public void update(ActivityDTO activityDto) {

        if (activityDto.getActivity_id() <= 0 || activityDto.getName() == null) {
            log.error("Invalid input data ");
            throw new InvalidUserException();
        }

        log.debug("Activity id : {}", activityDto.getActivity_id());
        log.debug("Activity name : {}", activityDto.getName());
        log.debug("Activity category_id : {}", activityDto.getActivity_category_id());

        Activity activity = getActivity(activityDto);
        activity.setId(activityDto.getActivity_id());

        log.debug("Activity transformed from activity DTO : {}", activity);

        Optional<Activity> activityExists = activityRepository.findById(activity.getId());
        log.debug("activityExists is : {}", activityExists);

        if (activityExists.isPresent()) {

            activityRepository.updateActivityById(activityDto.getActivity_id(),
                    activityDto.getName(), activityDto.getActivity_category_id());
        }
    }

    @Override
    public boolean delete(int id) {
        if (id <= 0) {return false;}

        activityRepository.deleteById(id);
        return true;

    }

    @Override
    public Page<ActivityDTO> findPaginatedSorted(Pageable pageable, String sortBy) {

        log.info("Start ActivityServiceImpl findPaginatedSorted method");
        log.info("String sortBy is {}", sortBy);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Pageable paging = PageRequest.of(currentPage, pageSize,
                Sort.by(sortBy).ascending());

        Page<ActivityDTO> pagedResult = activityRepository
                .findAll(paging).map(dtoMapper::mapToDto);

        log.debug("pagedResult is : {}",pagedResult);
        log.debug("Content of pagedResult is : {}",pagedResult.getContent());

        return pagedResult;

    }

    @Override
    public Page<ActivityDTO> findPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Pageable paging = PageRequest.of(currentPage, pageSize);

        Page<ActivityDTO> pagedResult = activityRepository
                .findAll(paging).map(dtoMapper::mapToDto);

        log.debug("pagedResult : {}",pagedResult);
        log.debug("Content : {}",pagedResult.getContent());

        return pagedResult;
    }

    @Override
    public List<Optional<ActivityDTO>> getActivityByCategory_Id(int categoryId) {
        if(categoryId <= 0) {return null;}
        return activityRepository.getActivityByCategoryId(categoryId)
                .stream()
                .map(x -> x.map(dtoMapper::mapToDto))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ActivityDTO> findPaginatedSortedFiltered(Pageable pageable,
                                                         String sortBy, String filterByCategory) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Pageable paging = PageRequest.of(currentPage, pageSize,
                Sort.by(filterByCategory).and(Sort.by(sortBy)).ascending());

        Page<ActivityDTO> pagedResult = activityRepository
                .findAll(paging).map(dtoMapper::mapToDto);

        log.debug("pagedResult : {}",pagedResult);
        log.debug("Content : {}",pagedResult.getContent());

        return pagedResult;
    }

    @Override
    public Page<ActivityDTO> findPaginatedSortByUsers(Pageable pageable) {

        log.info("Start UsersActivityServiceImpl findPaginatedSorted method");

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Pageable paging = PageRequest.of(currentPage, pageSize);

        Page<ActivityDTO> pagedResult = activityRepository
                .findPaginatedSortByUsers(paging).map(projectionMapper::mapToDto);

        log.debug("pagedResult is : {}",pagedResult);
        log.debug("Content of pagedResult is : {}",pagedResult.getContent());

        return pagedResult;

    }

    private Activity getActivity(ActivityDTO activityDto) {
        log.debug("ActivityService getActivity method START");
        return dtoMapper.mapFromDto(activityDto);
    }
}
