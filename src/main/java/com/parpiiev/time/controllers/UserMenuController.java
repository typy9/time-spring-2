package com.parpiiev.time.controllers;

import com.parpiiev.time.exceptions.request.InvalidRequestException;
import com.parpiiev.time.model.Status;
import com.parpiiev.time.exceptions.request.RequestAlreadyExistsException;
import com.parpiiev.time.services.interfaces.*;
import com.parpiiev.time.utils.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parpiiev.time.controllers.Paths.*;

@RequiredArgsConstructor
@Controller
public class UserMenuController {

    private static final Logger log = LoggerFactory.getLogger(UserMenuController.class);

    private final UserService<UserDTO> userService;
    private final ActivityService<ActivityDTO> activityService;
    private final ActivityRequestService<ActivityRequestDTO> activityRequestService;
    private final UsersActivityService<UsersActivityDTO> usersActivityService;


    @GetMapping(USER_MENU_PAGE)
    public String showPage(Model model, HttpSession session,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {

        log.info("Start UserMenuController showPage GET method");

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(2);

        UserDTO userDto = (UserDTO) session.getAttribute("user");
        Optional<UserDTO> namedUserDto = userService.getByLogin(userDto.getLogin());

        if (namedUserDto.isPresent()) {
            int userId = namedUserDto.get().getUser_id();

            Page<UsersActivityDTO> userActivitiesPage = usersActivityService.findPaginated(
                    PageRequest.of(currentPage - 1, pageSize), userId);
            List<UsersActivityDTO> usersActivities = userActivitiesPage.getContent();
            model.addAttribute("userActivitiesPage", userActivitiesPage);

            int totalPages = userActivitiesPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }

            model.addAttribute("userDTO", namedUserDto.get());
            model.addAttribute("usersActivity", usersActivities);
        } else {
            model.addAttribute("userDTO", userDto);
        }
        return USER_MENU_FILE;
    }

    @GetMapping(USER_MENU_UPDATE_ACTIVITY_PAGE)
    public String userUpdateTimePage(@PathVariable("id") int id,
                                     @PathVariable("activityId") int activityId,
                                     Model model, HttpSession session) {

        log.debug("Start UserMenuController GET userUpdateTimePage");

        UsersActivityDTO usersActivityDTO = new UsersActivityDTO();
        usersActivityDTO.setId(id);
        session.setAttribute("activityId", activityId);
        model.addAttribute("usersActivityDTO", usersActivityDTO);

        return USER_MENU_UPDATE_ACTIVITY_FILE;
    }

    @PostMapping(USER_MENU_UPDATE_ACTIVITY_TIME_PAGE)
    public String updateTime(@ModelAttribute("usersActivityDTO") @Valid UsersActivityDTO usersActivityDTO,
                             BindingResult bindingResult, HttpSession session,
                             @PathVariable("id") int id) {

        log.info("Start UserMenuController POST updateTime");
        log.debug("Activity Id that been updated is : {}", id);
        log.debug("usersActivityDTO retrieved from the form is {}", usersActivityDTO);

        if (bindingResult.hasErrors()) {
            return BACK_TO_USER_MENU_PAGE;
        }
        UserDTO userDto = (UserDTO) session.getAttribute("user");
        Optional<UserDTO> namedUserDto = userService.getByLogin(userDto.getLogin());

        if (namedUserDto.isPresent()) {
            int userId = namedUserDto.get().getUser_id();
            usersActivityDTO.setUser_id(userId);
            int activityId = (int) session.getAttribute("activityId");
            usersActivityDTO.setActivity_id(activityId);
        }

        log.debug("usersActivityDTO to update: {}", usersActivityDTO);
        usersActivityService.update(usersActivityDTO);

        return BACK_TO_USER_MENU_PAGE;
    }

    @GetMapping(USER_MENU_ADD_REQUEST_FORM_PAGE)
    public String showRequestForm(@PathVariable("id") int userId, Model model) {

        log.debug("Start UserMenuController GET showRequestForm");

        List<ActivityDTO> activity = new ArrayList<>();
        model.addAttribute("activity", activity);
        List<ActivityDTO> activities =  activityService.getAll();
        model.addAttribute("activities", activities);

        model.addAttribute("userId", userId);
        return USER_MENU_ADD_REQUEST_FORM_FILE;
    }

    @PostMapping(USER_MENU_ADD_REQUEST_POST_PAGE)
    public String addRequestForm(Model model, @ModelAttribute @Valid ActivityRequestDTO activityRequestDTO,
                                 BindingResult bindingResult, @RequestParam int activityId,
                                 HttpSession session) {

        log.debug("Start UserMenuController POST addRequestForm");

        UserDTO userDto = (UserDTO) session.getAttribute("user");
        Optional<UserDTO> namedUserDto = userService.getByLogin(userDto.getLogin());

        if (namedUserDto.isPresent()) {
            int userId = namedUserDto.get().getUser_id();

            ActivityRequestDTO request = new ActivityRequestDTO();
            request.setUser_id(userId);
            request.setActivity_id(activityId);
            request.setStatus(Status.CREATED);
            try {
                activityRequestService.save(request);
            } catch (RequestAlreadyExistsException e) {
                log.debug("RequestAlreadyExistsException caught");
                return BACK_TO_USER_MENU_PAGE;
            }

        }
        return BACK_TO_USER_MENU_PAGE;
    }

    @GetMapping(USER_MENU_SEND_DELETE_REQUEST)
    public String sendDeleteRequest(HttpSession session, Model model,
                                    @PathVariable String activityId){

        log.info("Start UserMenuController GET sendDeleteRequest");

        UserDTO userDto = (UserDTO) session.getAttribute("user");
        Optional<UserDTO> namedUserDto = userService.getByLogin(userDto.getLogin());

        ActivityRequestDTO request = new ActivityRequestDTO();
        request.setUser_id(Integer.parseInt(String.valueOf(namedUserDto.get().getUser_id())));
        request.setActivity_id(Integer.parseInt(activityId));
        request.setStatus(Status.TOBEDELETED);
        try {
            activityRequestService.update(request);
        } catch (InvalidRequestException e){
            log.error("InvalidRequestException", e);
            return BACK_TO_USER_MENU_FILE;
        }

        model.addAttribute("userDTO", namedUserDto.get());
        return BACK_TO_USER_MENU_FILE;
    }
}
