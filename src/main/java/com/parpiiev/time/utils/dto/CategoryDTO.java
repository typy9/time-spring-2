package com.parpiiev.time.utils.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CategoryDTO {

    private int category_id;

    @NotEmpty(message = "Name can not be empty")
    @Size(min=2, max=30, message = "Name should be between 2 and 30 characters")
    private String name;

}
