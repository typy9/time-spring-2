package com.parpiiev.time.utils.projections;

import com.parpiiev.time.utils.dto.ActivityDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper class to map from ActivityProjection to ActivityDTO class
 */
@Component
public class ActivityProjectionMapper {

    public ActivityDTO mapToDto(ActivityProjection activityProjection) {

        ActivityDTO dto = new ActivityDTO();

        dto.setActivity_id(activityProjection.getId());
        dto.setName(activityProjection.getName());
        dto.setActivity_category_id(activityProjection.getCategory_id());
        return dto;
    }
}
