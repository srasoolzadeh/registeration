package uni.edu.registration.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uni.edu.registration.models.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by rasoolzadeh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String title;
    private String body;
    @NotNull
    private User user;
}
