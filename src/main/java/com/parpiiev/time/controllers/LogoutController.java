package com.parpiiev.time.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

import static com.parpiiev.time.controllers.Paths.INDEX_PAGE;
import static com.parpiiev.time.controllers.Paths.LOGOUT_PAGE;

@Controller
public class LogoutController {

    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    @GetMapping(LOGOUT_PAGE)
    public RedirectView getLogout(HttpServletRequest request, SessionStatus sessionStatus) {

        log.info("Start LogoutController GET getLogout");

        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("login");
        request.getSession().removeAttribute("password");
        request.getSession().removeAttribute("role");
        request.getSession().setAttribute("userInSystem", false);
        request.getSession().invalidate();
        sessionStatus.setComplete();

        return new RedirectView(INDEX_PAGE);
    }
}
