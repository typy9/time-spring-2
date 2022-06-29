package com.parpiiev.time.utils.dto;

import com.parpiiev.time.annotations.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserDTO {

    private int user_id;
    @NotEmpty(message = "Name can not be empty")
    @Size(min=2, max=30, message = "Name should be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "Login can not be empty")
    @Size(min=1, max=15, message = "Login should be between 1 and 15 characters")
    private String login;
    @ValidPassword
    private String password;

    private String role;

}
