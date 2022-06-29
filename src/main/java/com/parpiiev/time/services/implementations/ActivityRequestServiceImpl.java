package com.parpiiev.time.services.implementations;

import com.parpiiev.time.exceptions.request.InvalidRequestException;
import com.parpiiev.time.exceptions.request.RequestAlreadyExistsException;
import com.parpiiev.time.model.ActivityRequest;
import com.parpiiev.time.exceptions.user.InvalidUserException;
import com.parpiiev.time.repository.ActivityRequestRepository;
import com.parpiiev.time.services.interfaces.ActivityRequestService;
import com.parpiiev.time.utils.dto.ActivityRequestDTO;
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
public class ActivityRequestServiceImpl implements ActivityRequestService<ActivityRequestDTO> {

    private final Logger log = LoggerFactory.getLogger(ActivityRequestServiceImpl.class);

    private final ActivityRequestRepository activityRequestRepository;
    private final DtoMapper<ActivityRequestDTO, ActivityRequest> dtoMapper;


    @Override
    public Optional<ActivityRequestDTO> getById(int id) {
        if (id <= 0) {
            return Optional.empty();
        }
        return activityRequestRepository.findById(id).map(dtoMapper::mapToDto);
    }

    @Override
    public List<ActivityRequestDTO> getAll() {
        return activityRequestRepository.findAll().stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ActivityRequestDTO> getByUserIdActivityId(int userId, int activityId) {

        if (userId <= 0 || activityId <= 0) {
            return Optional.empty();
        }
        return activityRequestRepository.findByUserIdActivityId(userId, activityId)
                .map(dtoMapper::mapToDto);
    }

    @Override
    public boolean save(ActivityRequestDTO activityRequestDTO) {
        boolean flag;
        if (activityRequestDTO.getUser_id() <= 0
                || activityRequestDTO.getActivity_id() <= 0
                || activityRequestDTO.getStatus() == null) {

            log.error("Invalid input data");
            throw new InvalidUserException("User input data is not valid");
        }
        ActivityRequest activityRequest = getActivityRequest(activityRequestDTO);
        if (getByUserIdActivityId(activityRequest.getUser().getId(),
                activityRequest.getActivity().getId()).isPresent()) {
            throw new RequestAlreadyExistsException();
        } else {
            flag = true;
            activityRequestRepository.save(activityRequest);
        }
        return flag;
    }

    @Override
    public void update(ActivityRequestDTO activityRequestDTO) {
        if (activityRequestDTO.getUser_id() <= 0
                || activityRequestDTO.getActivity_id() <= 0
                || activityRequestDTO.getStatus() == null) {

            log.error("Invalid input data");
            throw new InvalidRequestException("User input data is not valid");
        }

        ActivityRequest activityRequest = getActivityRequest(activityRequestDTO);
        activityRequest.setRequest_id(activityRequestDTO.getRequest_id());

        Optional<ActivityRequest> requestExists = activityRequestRepository
                .findByUserIdActivityId(activityRequest.getUser().getId(),
                        activityRequest.getActivity().getId());

        requestExists.ifPresent(request -> activityRequestRepository
                .updateRequestById(request.getRequest_id(),
                activityRequest.getStatus()));
    }

    @Override
    public boolean delete(int id) {
        if (id <= 0) {return false;}
        activityRequestRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Optional<ActivityRequestDTO>> getAllByUserId(int id) {
        return activityRequestRepository.findAllByUserId(id)
                .stream().map(x -> x.map(dtoMapper::mapToDto))
                .collect(Collectors.toList());
    }

    private ActivityRequest getActivityRequest(ActivityRequestDTO activityRequestDto) {
        return dtoMapper.mapFromDto(activityRequestDto);
    }

    @Override
    public Page<ActivityRequestDTO> findPaginated(Pageable pageable) {
        log.info("Start CategoryServiceImpl findPaginated method");

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Pageable paging = PageRequest.of(currentPage, pageSize);
        Page<ActivityRequestDTO> pagedResult = activityRequestRepository.findAll(paging).map(dtoMapper::mapToDto);

        log.debug("pagedResult : {}",pagedResult);
        log.debug("Content : {}",pagedResult.getContent());

        return pagedResult;
    }
}
