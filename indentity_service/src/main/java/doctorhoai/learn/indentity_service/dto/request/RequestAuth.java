package doctorhoai.learn.indentity_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAuth {
    @NotBlank(message = "Phone number can't be blank")
    @Pattern(regexp = "^(0\\d{9})$", message = "Phone number must be 10 digits and start with 0")
    private String username;
    @NotBlank(message = "Password can't be blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character"
    )
    private String password;
}
