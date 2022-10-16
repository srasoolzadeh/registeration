package uni.edu.registration.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by rasoolzadeh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {
    private String username;
    private String password;
    @NotNull
    @NotBlank
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean enabled;
}
