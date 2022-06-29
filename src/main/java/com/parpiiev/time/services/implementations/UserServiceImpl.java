package com.parpiiev.time.services.implementations;

import com.parpiiev.time.exceptions.user.UserAlreadyExistsException;
import com.parpiiev.time.model.User;
import com.parpiiev.time.exceptions.user.InvalidUserException;
import com.parpiiev.time.repository.UserRepository;
import com.parpiiev.time.services.interfaces.UserService;
import com.parpiiev.time.utils.dto.UserDTO;
import com.parpiiev.time.utils.dto.mappers.DtoMapper;
import com.parpiiev.time.utils.validators.PatternMatcher;
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
 * Service class for Users table
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService<UserDTO> {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final DtoMapper<UserDTO, User> dtoMapper;
    private final UserRepository userRepository;

    @Override
    public Optional<UserDTO> getById(int id) {
        if (id <= 0) {
            return Optional.empty();
        }
        return userRepository.findById(id).map(dtoMapper::mapToDto);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(dtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getByLogin(String login) {

        if (!PatternMatcher.validateLogin(login)) {
            return Optional.empty();
        }
        return userRepository.findByLogin(login).map(dtoMapper::mapToDto);
    }

    @Override
    public boolean save(UserDTO userDto) {
        boolean flag;
        if (userDto.getLogin() == null
                || userDto.getPassword() == null
                || userDto.getRole() == null) {
            log.error("Invalid input data");
            throw new InvalidUserException("User input data is not valid");
        }

        User user = getUser(userDto);

        if (getByLogin(user.getLogin()).isPresent()) {
            throw new UserAlreadyExistsException();
        } else {
            flag = true;
            userRepository.save(user);
        }
        return flag;
    }

    private User getUser(UserDTO userDto) {
        // TODO add password encryption
        String passwordToSave = userDto.getPassword();
        userDto.setPassword(passwordToSave);
        return dtoMapper.mapFromDto(userDto);
    }

    @Override
    public void update(UserDTO userDto) {

        if (userDto.getUser_id() <= 0 || userDto.getLogin() == null ||
                userDto.getPassword() == null || userDto.getRole() == null) {
            log.error("Invalid input data ");
            throw new InvalidUserException();
        }

        log.debug("User is : {}", userDto);

        Optional<User> userExists = userRepository.findById(userDto.getUser_id());
        log.debug("userExists is : {}", userExists);

        if (userExists.isPresent()) {
            User userToUpdate = getUser(userDto);
            userRepository.updateUserById(userExists.get().getId(), userToUpdate.getUsername(),
                    userToUpdate.getLogin(), userToUpdate.getPassword());
        }
    }


    @Override
    public boolean delete(int id) {
        if (id <= 0) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public String getUserRole(UserDTO userDTO) {

        if (userDTO.getLogin() == null || userDTO.getPassword() == null) {
            log.error("Invalid input data");
            throw new InvalidUserException("User input data is not valid");
        }

        return userRepository.getUserRole(userDTO.getLogin(), userDTO.getPassword()).orElse(null);

        //        User user = getUser(userDTO);
//        String role;
//
//        try {
//            role = userDao.getRole(user);
//        } catch (DuplicateValueException e) {
//            log.error(e.getMessage());
//            throw new UserAlreadyExistsException(e.getMessage());
//        }
//        return role;
    }

    @Override
    public Page<UserDTO> findPaginated(Pageable pageable) {
        log.info("Start UserServiceImpl findPaginated method");

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Pageable paging = PageRequest.of(currentPage, pageSize);
        Page<UserDTO> pagedResult = userRepository.findAll(paging).map(dtoMapper::mapToDto);

        log.debug("pagedResult : {}",pagedResult);
        log.debug("Content : {}",pagedResult.getContent());

        return pagedResult;
    }
}
