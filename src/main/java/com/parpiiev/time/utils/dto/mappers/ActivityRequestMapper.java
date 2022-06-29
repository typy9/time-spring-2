package com.parpiiev.time.utils.dto.mappers;

import com.parpiiev.time.model.*;
import com.parpiiev.time.utils.dto.ActivityRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ActivityRequestMapper implements DtoMapper<ActivityRequestDTO, ActivityRequest>{

    @Override
    public ActivityRequestDTO mapToDto(ActivityRequest activityRequest) {
        ActivityRequestDTO dto = new ActivityRequestDTO();
        dto.setRequest_id(activityRequest.getRequest_id());
        dto.setUser_id(activityRequest.getUser().getId());
        dto.setActivity_id(activityRequest.getActivity().getId());
        dto.setStatus(activityRequest.getStatus());
        return dto;
    }

    @Override
    public ActivityRequest mapFromDto(ActivityRequestDTO activityRequestDto) {
        ActivityRequest activityRequest= new ActivityRequest();

        User user = new User();
        user.setUserId(activityRequestDto.getUser_id());
        activityRequest.setUser(user);

        Activity activity = new Activity();
        activity.setId(activityRequestDto.getActivity_id());
        activityRequest.setActivity(activity);

        activityRequest.setStatus(activityRequestDto.getStatus());
        return activityRequest;
    }
}
