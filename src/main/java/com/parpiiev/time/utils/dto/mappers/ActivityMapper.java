package com.parpiiev.time.utils.dto.mappers;

import com.parpiiev.time.model.Activity;
import com.parpiiev.time.model.Category;
import com.parpiiev.time.utils.dto.ActivityDTO;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper implements DtoMapper<ActivityDTO, Activity>{

    @Override
    public ActivityDTO mapToDto(Activity activity) {

        ActivityDTO dto = new ActivityDTO();
        dto.setActivity_id(activity.getId());
        dto.setName(activity.getName());
        dto.setActivity_category_id(activity.getCategory().getId());
        return dto;
    }

    @Override
    public Activity mapFromDto(ActivityDTO activityDTO) {
        Activity activity = new Activity();

        activity.setName(activityDTO.getName());

        Category category = new Category();
        category.setId(activityDTO.getActivity_category_id());
        activity.setCategory(category);

        return activity;
    }
}
