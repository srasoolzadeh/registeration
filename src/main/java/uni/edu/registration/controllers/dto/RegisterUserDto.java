package uni.edu.registration.controllers.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by rasoolzadeh
 */
@Data
public class RegisterUserDto {
    @NotNull(message = "invalid username")
    @NotEmpty(message = "invalid username")
    private String username;
    @NotNull(message = "invalid password")
    @NotEmpty(message = "invalid password")
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
