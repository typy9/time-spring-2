package com.parpiiev.time.controllers;

import com.parpiiev.time.exceptions.user.UserAlreadyExistsException;
import com.parpiiev.time.services.interfaces.UserService;
import com.parpiiev.time.utils.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.parpiiev.time.controllers.Paths.*;

@RequiredArgsConstructor
@Controller
public class RegistrationController {
    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
    private final UserService<UserDTO> userService;


    @GetMapping(REGISTRATION_PAGE)
    public String getRegisterPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return REGISTRATION_FILE;
    }

    @PostMapping(REGISTRATION_PAGE)
    public String registerUser(Model model, @ModelAttribute("userDTO") @Valid UserDTO userDTO,
                               BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return REGISTRATION_FILE;
        }

        String name = userDTO.getName();
        String login = userDTO.getLogin();
        String password = userDTO.getPassword();
        String role = "user";
        if (login == null || password == null || name == null) {
            model.addAttribute("isInvalidData", true);
            return REGISTRATION_FILE;
        }

        boolean isSaved = false;

        try {
            userDTO.setRole(role);
            isSaved = userService.save(userDTO);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("isUserExists", true);
            log.warn("Such user is already exists", e);
            return REGISTRATION_FILE;
        }

        if (isSaved) {
            model.addAttribute("userRegistered", true);
        }
        return INDEX_FILE;
    }
}
