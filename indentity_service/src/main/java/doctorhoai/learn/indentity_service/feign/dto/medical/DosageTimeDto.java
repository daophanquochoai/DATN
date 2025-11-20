package doctorhoai.learn.indentity_service.feign.dto.medical;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DosageTimeDto {
    private UUID timeId;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String description;
}
