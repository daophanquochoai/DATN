package doctorhoai.learn.indentity_service.feign.dto.medical;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitsDto {
    private UUID unitId;
    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String name;
    private String description;
}
