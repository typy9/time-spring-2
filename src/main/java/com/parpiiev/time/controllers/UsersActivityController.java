package com.parpiiev.time.controllers;

import com.parpiiev.time.services.interfaces.UsersActivityService;
import com.parpiiev.time.utils.dto.UsersActivityDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parpiiev.time.controllers.Paths.*;

@RequiredArgsConstructor
@Controller
public class UsersActivityController {

    private static final Logger log = LoggerFactory.getLogger(UsersActivityController .class);

    private final UsersActivityService<UsersActivityDTO> usersActivityService;


    @GetMapping(ALL_USERS_ACTIVITY_PAGE)
    public String listUserActivities(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {

        log.debug("Start UsersActivityController GET listUserActivities");

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(2);

        Page<UsersActivityDTO> recordPage = usersActivityService.findPaginatedFull(
                PageRequest.of(currentPage - 1, pageSize));
        List<UsersActivityDTO> usersActivity = recordPage.getContent();
        model.addAttribute("recordPage", recordPage);

        int totalPages = recordPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("usersActivity", usersActivity);
        model.addAttribute("UsersActivityDTO", new UsersActivityDTO());

        return ALL_USERS_ACTIVITY_FILE;
    }

    @GetMapping(USERS_ACTIVITY_DELETE_PAGE)
    public String deleteUsersActivity(@PathVariable("id") int id, Model model) {

        log.debug("Start UsersActivityController GET deleteUsersActivity");

        Optional<UsersActivityDTO> usersActivity = usersActivityService.getById(id);

        if(usersActivity.isPresent()) {
            usersActivityService.delete(id);
            List<UsersActivityDTO> usersActivityList = usersActivityService.getAll();
            model.addAttribute("usersActivity", usersActivityList);
        }
        return BACK_TO_ALL_USERS_ACTIVITY_PAGE;
    }

    @GetMapping(USERS_ACTIVITY_UPDATE_PAGE)
    public String editUsersActivity(@PathVariable("id") int id,
                                    @PathVariable("user_id") String userId,
                                    @PathVariable("activityId") int activityId,
                                    Model model, HttpSession session) {

        log.debug("Start UsersActivityController GET editUsersActivity");

        UsersActivityDTO usersActivityDTO = new UsersActivityDTO();
        usersActivityDTO.setId(id);
        session.setAttribute("recordId", id);
        session.setAttribute("userId", userId);
        session.setAttribute("activityId", activityId);
        model.addAttribute("usersActivityDTO", usersActivityDTO);

        return ADMIN_MENU_UPDATE_ACTIVITY_FILE;
    }

    @PostMapping(ADMIN_MENU_UPDATE_ACTIVITY_TIME_PAGE)
    public String updateTime(@ModelAttribute("usersActivityDTO") @Valid UsersActivityDTO usersActivityDTO,
                             BindingResult bindingResult, HttpSession session,
                             @PathVariable("id") int id) {

        log.info("Start UsersActivityController POST updateTime");
        log.debug("Activity Id to update is : {}", id);
        log.debug("usersActivityDTO is {}", usersActivityDTO);

        if (bindingResult.hasErrors()) {
            return BACK_TO_ALL_USERS_ACTIVITY_PAGE;
        }

        int recordId = (int) session.getAttribute("recordId");
        Optional<UsersActivityDTO> usersActivityDto = usersActivityService.getById(recordId);

        if (usersActivityDto.isPresent()) {

            usersActivityDTO.setId(recordId);

            Object userId = session.getAttribute("userId");
            usersActivityDTO.setUser_id(Integer.parseInt(userId.toString()));

            int activityId = (int) session.getAttribute("activityId");
            usersActivityDTO.setActivity_id(activityId);

            usersActivityService.update(usersActivityDTO);
        }
        return BACK_TO_ALL_USERS_ACTIVITY_PAGE;
    }
}
