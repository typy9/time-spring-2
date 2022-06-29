package com.parpiiev.time.controllers.filters;

import com.parpiiev.time.utils.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.parpiiev.time.controllers.Paths.*;

@Component
@Order(1)
public class AuthorizationFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession();
        String requestPath = request.getServletPath();

        log.info("Starting a transaction for req : {}", request.getRequestURI());

        UserDTO userDTO = (UserDTO) session.getAttribute("user");


        if (userDTO == null) {

            if (requestPath.equals("/")) {

                UserDTO userDto = new UserDTO();
                userDto.setRole("unknown");
                session.setAttribute("user", userDto);

                response.sendRedirect(INDEX_PAGE);
            } else {
                response.sendRedirect(ERROR_ACCESS_FILE);
            }

        } else if (requestPath.contains("/user") && !userDTO.getRole().equals("user")) {
            response.sendRedirect(ERROR_ACCESS_FILE);

        } else if (requestPath.contains("/admin") && !userDTO.getRole().equals("admin")) {
            response.sendRedirect(ERROR_ACCESS_FILE);
        }

        filterChain.doFilter(request, response);
        log.info("Committing a transaction for req : {}", request.getRequestURI());
    }

    @Override
    public void destroy() {
    }
}