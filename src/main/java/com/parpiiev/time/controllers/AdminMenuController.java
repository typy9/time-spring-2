package com.parpiiev.time.controllers;

import com.parpiiev.time.utils.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.parpiiev.time.controllers.Paths.ADMIN_MENU_FILE;
import static com.parpiiev.time.controllers.Paths.ADMIN_MENU_PAGE;

@Controller
public class AdminMenuController {

    private static final Logger log = LoggerFactory.getLogger(AdminMenuController.class);

    @GetMapping(ADMIN_MENU_PAGE)
    public String showPage(Model model) {
        log.debug("Start AdminMenuController GET");
        model.addAttribute("userDTO", new UserDTO());
        return ADMIN_MENU_FILE;
    }
}
