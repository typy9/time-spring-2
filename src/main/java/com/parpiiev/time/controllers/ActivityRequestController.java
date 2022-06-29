package com.parpiiev.time.controllers;

import com.parpiiev.time.model.Status;
import com.parpiiev.time.exceptions.users_activity.UsersActivityAlreadyExistsException;
import com.parpiiev.time.services.interfaces.ActivityRequestService;
import com.parpiiev.time.services.interfaces.UsersActivityService;
import com.parpiiev.time.utils.dto.ActivityRequestDTO;
import com.parpiiev.time.utils.dto.UsersActivityDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parpiiev.time.controllers.Paths.*;

@RequiredArgsConstructor
@Controller
public class ActivityRequestController {

    private static final Logger log = LoggerFactory.getLogger(ActivityRequestController.class);

    private final ActivityRequestService<ActivityRequestDTO> activityRequestService;
    private final UsersActivityService<UsersActivityDTO> usersActivityService;


    @GetMapping(ALL_REQUESTS_PAGE)
    public String listRequests(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {

        log.debug("Start ActivityRequestController GET listRequests");

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(2);

        Page<ActivityRequestDTO> requestPage = activityRequestService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize));
        List<ActivityRequestDTO> requests = requestPage.getContent();
        model.addAttribute("requestPage", requestPage);

        int totalPages = requestPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("requests", requests);
        model.addAttribute("activityRequestDTO", new ActivityRequestDTO());

        return ALL_REQUESTS_FILE;
    }

    @GetMapping(REQUEST_DECLINE_PAGE)
    public String declineRequest(@PathVariable("id") int id, Model model) {

        log.debug("Start ActivityRequestController GET declineRequest");

        Optional<ActivityRequestDTO> request = activityRequestService.getById(id);

        if(request.isPresent()) {
            request.get().setStatus(Status.DECLINED);
            activityRequestService.update(request.get());
            List<ActivityRequestDTO> requests = activityRequestService.getAll();
            model.addAttribute("requests", requests);
        }
        return BACK_TO_ALL_REQUESTS_PAGE;
    }

    @GetMapping(REQUEST_APPROVE_PAGE)
    public String approveRequest(@PathVariable("id") int id, Model model) {

        log.debug("Start ActivityRequestController GET approveRequest");

        Optional<ActivityRequestDTO> request = activityRequestService.getById(id);

        if(request.isPresent() &&
                !request.get().getStatus().equals(Status.APPROVED)) {

            request.get().setStatus(Status.APPROVED);
            activityRequestService.update(request.get());
            List<ActivityRequestDTO> requests = activityRequestService.getAll();
            model.addAttribute("requests", requests);

            UsersActivityDTO usersActivityDto = new UsersActivityDTO();
            usersActivityDto.setUser_id(request.get().getUser_id());
            usersActivityDto.setActivity_id(request.get().getActivity_id());

            usersActivityDto.setTime(0);

            try {
                usersActivityService.save(usersActivityDto);
            } catch (UsersActivityAlreadyExistsException e) {
                log.debug("UsersActivityAlreadyExistsException caught");
                return BACK_TO_ALL_REQUESTS_PAGE;
            }
        }
        return BACK_TO_ALL_REQUESTS_PAGE;
    }

    @GetMapping(REQUEST_DELETE_PAGE)
    public String deleteRequest(@PathVariable("id") int id,
                                @PathVariable("userId") int userId,
                                @PathVariable("activityId") int activityId,
                                @PathVariable("status") String status) {

        Status requestStatus = Status.valueOf(status);

        activityRequestService.delete(id);

        if (requestStatus.equals(Status.TOBEDELETED)){

            Optional<UsersActivityDTO> recordToDelete = usersActivityService
                    .getByUserIdActivityId(userId, activityId);
            recordToDelete.ifPresent(
                    usersActivityDTO -> usersActivityService.delete(usersActivityDTO.getId())
            );
        }

        return BACK_TO_ALL_REQUESTS_PAGE;
    }
}
