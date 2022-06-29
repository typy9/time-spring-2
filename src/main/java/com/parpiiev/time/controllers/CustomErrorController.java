package com.parpiiev.time.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static com.parpiiev.time.controllers.Paths.*;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(ERROR_ACCESS_FILE)
    public String handleAccessError(HttpServletRequest request) {
        return ERROR_ACCESS_FILE;
    }

    @RequestMapping(ERROR_FILE)
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return ERROR_FILE_404;
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ERROR_FILE_500;
            }
        }
        return ERROR_FILE;
    }
}