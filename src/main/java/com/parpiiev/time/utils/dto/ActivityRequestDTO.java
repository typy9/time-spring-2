package com.parpiiev.time.utils.dto;

import com.parpiiev.time.model.Status;
import lombok.*;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ActivityRequestDTO {

    private int request_id;

    private int user_id;

    private int activity_id;

    private Status status;
}
