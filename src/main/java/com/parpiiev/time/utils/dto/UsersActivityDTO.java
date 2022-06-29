package com.parpiiev.time.utils.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsersActivityDTO {

    private int id;

    private int user_id;

    private int activity_id;

    @Min(value=0, message = "Time should be greater then 0 minutes")
    @Max(value=480, message = "Time should not be greater then 480 minutes (8 hours)")
    private int time;

}
