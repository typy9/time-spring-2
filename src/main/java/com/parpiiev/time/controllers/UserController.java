package com.parpiiev.time.controllers;

import com.parpiiev.time.exceptions.user.InvalidUserException;
import com.parpiiev.time.services.interfaces.UserService;
import com.parpiiev.time.utils.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parpiiev.time.controllers.Paths.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService<UserDTO> userService;


    @GetMapping(ALL_USERS_PAGE)
    public String listUsers(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {

        log.debug("Start UserController GET listUsers");

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(2);

        Page<UserDTO> userPage = userService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize));
        List<UserDTO> users = userPage.getContent();
        model.addAttribute("userPage", userPage);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

//        List<UserDTO> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("userDTO", new UserDTO());

        return ALL_USERS_FILE;
    }

    @PostMapping(ALL_USERS_PAGE)
    public String addUser(Model model, @ModelAttribute @Valid UserDTO userDTO,
                               BindingResult bindingResult){

        log.debug("Start UserController GET addUser");

        if (bindingResult.hasErrors()) {
            List<UserDTO> users = userService.getAll();
            model.addAttribute("users", users);
            return ALL_USERS_PAGE;
        }

        String name = userDTO.getName();
        String login = userDTO.getLogin();
        String password = userDTO.getPassword();
        userDTO.setRole("user");

        if (login == null || password == null || name == null) {
            model.addAttribute("isInvalidData", true);
            return ALL_USERS_PAGE;
        }

        boolean isSaved = false;

        try {
            isSaved = userService.save(userDTO);
        } catch (RuntimeException e) {
            model.addAttribute("isUserExists", true);
            log.warn("Such user already exists", e);
        }

        if (isSaved) {
            model.addAttribute("userRegistered", true);
        }
        List<UserDTO> users = userService.getAll();
        model.addAttribute("users", users);
        return BACK_TO_ALL_USERS_PAGE;
    }

    @GetMapping(USER_DELETE_PAGE)
    public String deleteUser(@PathVariable("id") int id, Model model) {

        log.debug("Start UserController GET deleteUser");

        Optional<UserDTO> user = userService.getById(id);

        if(user.isPresent()) {
            userService.delete(id);
            List<UserDTO> users = userService.getAll();
            model.addAttribute("users", users);
        }
        return BACK_TO_ALL_USERS_PAGE;
    }

    @GetMapping(USER_UPDATE_PAGE)
    public String userUpdatePage(@PathVariable("id") int id, Model model) {

        log.debug("Start UserController GET userUpdatePage");

        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(id);
        model.addAttribute("userDTO", userDTO);
        return USER_UPDATE_FILE;
    }

    @PostMapping(USER_UPDATE_PAGE)
    public String updateUser(@PathVariable("id") int id, @Valid UserDTO userDTO,
                             BindingResult bindingResult, Model model) {

        log.info("Start UserController POST updateUser");
        log.debug("ID is : {}", id);
        log.debug("userDTO retrieved from the form is {}", userDTO);

        if (bindingResult.hasErrors()) {
            return BACK_TO_ALL_USERS_PAGE;
        }

        userDTO.setUser_id(id);
        try {
            userService.update(userDTO);
        } catch (InvalidUserException e) {
            log.error(String.valueOf(e));
            return BACK_TO_ALL_USERS_PAGE;
        }
        List<UserDTO> users = userService.getAll();
        model.addAttribute("users", users);
        return BACK_TO_ALL_USERS_PAGE;
    }
}
