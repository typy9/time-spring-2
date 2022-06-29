package com.parpiiev.time.controllers;

import com.parpiiev.time.services.interfaces.UserService;
import com.parpiiev.time.utils.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

import static com.parpiiev.time.controllers.Paths.*;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final UserService<UserDTO> userService;


    @GetMapping(INDEX_PAGE)
    public String indexPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return INDEX_FILE;
    }

    @PostMapping(INDEX_PAGE)
    public String executeLogin(Model model, @ModelAttribute UserDTO userDTO, HttpSession session) {

        log.debug("Start executing doPost in LoginController");

        String login = userDTO.getLogin();
        String password = userDTO.getPassword();

        if (login == null || password == null) {
            model.addAttribute("isInvalidData", true);
            return "redirect:"+INDEX_FILE;
        }

        String userRole = userService.getUserRole(userDTO);

        if (userRole == null) {
            model.addAttribute("isInvalidData", true);
            return INDEX_FILE;
        }

        userDTO.setRole(userRole);

        switch (userRole) {
            case "admin" -> {
                log.trace("user role is ADMIN");
                model.addAttribute("role", "admin");
                session.setAttribute("user", userDTO);
                return "redirect:"+ADMIN_MENU_PAGE;
            }
            case "user" -> {
                log.trace("user role is USER");
                model.addAttribute("role", "user");
                session.setAttribute("user", userDTO);
                return "redirect:"+USER_MENU_PAGE;

            }
            default -> {
                log.trace("user role is UNKNOWN");
                return "redirect:"+INDEX_FILE;
            }
        }
    }
}
