package com.parpiiev.time.services.implementations;

import com.parpiiev.time.exceptions.user.UserAlreadyExistsException;
import com.parpiiev.time.model.UsersActivity;
import com.parpiiev.time.exceptions.user.InvalidUserException;
import com.parpiiev.time.exceptions.users_activity.InvalidUsersActivityException;
import com.parpiiev.time.repository.UsersActivityRepository;
import com.parpiiev.time.services.interfaces.UsersActivityService;
import com.parpiiev.time.utils.dto.UsersActivityDTO;
import com.parpiiev.time.utils.dto.mappers.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for UsersActivity table
 */
@RequiredArgsConstructor
@Service
public class UsersActivityServiceImpl implements UsersActivityService<UsersActivityDTO> {

    private final Logger log = LoggerFactory.getLogger(UsersActivityServiceImpl.class);
    private final UsersActivityRepository usersActivityRepository;
    private final DtoMapper<UsersActivityDTO, UsersActivity> dtoMapper;


    @Override
    public Optional<UsersActivityDTO> getById(int id) {
        if (id <= 0) {
            return Optional.empty();
        }
        return usersActivityRepository.findById(id).map(dtoMapper::mapToDto);
    }

    @Override
    public List<UsersActivityDTO> getAll() {
        return usersActivityRepository.findAll().stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UsersActivityDTO> getByUserIdActivityId(int userId, int activityId) {

        if (userId <= 0 || activityId <= 0) {
            return Optional.empty();
        }
        return usersActivityRepository.findByUserIdActivityId(userId, activityId)
                .map(dtoMapper::mapToDto);
    }

    @Override
    public boolean save(UsersActivityDTO usersActivityDTO) {
        boolean flag;
        if (usersActivityDTO.getUser_id() <= 0
                || usersActivityDTO.getActivity_id() <= 0
                || usersActivityDTO.getTime() < 0) {

            log.error("Invalid input data");
            throw new InvalidUserException("UserActivity input data is not valid");
        }
        UsersActivity usersActivity = getUsersActivity(usersActivityDTO);

        if (getByUserIdActivityId(usersActivity.getUser().getId(),
                usersActivity.getActivity().getId()).isPresent()) {
            throw new UserAlreadyExistsException();
        } else {
            flag = true;
            usersActivityRepository.save(usersActivity);
        }
        return flag;
    }

    @Override
    public void update(UsersActivityDTO usersActivityDTO) {

        if (usersActivityDTO.getUser_id() <= 0
                || usersActivityDTO.getTime() < 0
                || usersActivityDTO.getTime() > 480) {
            log.error("Invalid input data ");
            throw new InvalidUsersActivityException();
        }

        UsersActivity usersActivity = getUsersActivity(usersActivityDTO);
        usersActivity.setId(usersActivityDTO.getId());

        log.debug("User transformed from usersActivity DTO : {}", usersActivity);

        Optional<UsersActivity> usersActivityExists = usersActivityRepository.findById(usersActivity.getId());
        log.debug("usersActivityExists object is : {}", usersActivityExists);

        if (usersActivityExists.isPresent()) {
            usersActivityRepository.updateRecordById(usersActivity.getId(), usersActivity.getTime());
        }
    }

    @Override
    public boolean delete(int id) {
        if (id <= 0) {return false;}

        usersActivityRepository.deleteById(id);
        return true;
    }

    /**
     * Find all user's activities by User's id
     * @param id user's id
     * @return List of user's activities
     */
    @Override
    public List<UsersActivityDTO> getAllById(int id) {

        return usersActivityRepository.findAllUserRecordsById(id)
                .stream().map(dtoMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Page<UsersActivityDTO> findPaginated(Pageable pageable, int userId) {
        log.info("Start UsersActivityServiceImpl findPaginated method");

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Pageable paging = PageRequest.of(currentPage, pageSize);
        Page<UsersActivityDTO> pagedResult = usersActivityRepository
                .findPaginatedByUserId(paging, userId).map(dtoMapper::mapToDto);

        log.debug("pagedResult : {}",pagedResult);
        log.debug("Content : {}",pagedResult.getContent());

        return pagedResult;
    }

    @Override
    public Page<UsersActivityDTO> findPaginatedFull(Pageable pageable) {
        log.info("Start UsersActivityServiceImpl findPaginated method");

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Pageable paging = PageRequest.of(currentPage, pageSize);
        Page<UsersActivityDTO> pagedResult = usersActivityRepository.findAll(paging).map(dtoMapper::mapToDto);

        log.debug("pagedResult : {}", pagedResult);
        log.debug("Content : {}", pagedResult.getContent());

        return pagedResult;
    }

    private UsersActivity getUsersActivity(UsersActivityDTO usersActivityDTO) {
        return dtoMapper.mapFromDto(usersActivityDTO);
    }
}
