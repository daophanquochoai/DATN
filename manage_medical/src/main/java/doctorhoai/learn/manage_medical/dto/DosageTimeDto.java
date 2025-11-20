package doctorhoai.learn.manage_medical.dto;

import doctorhoai.learn.manage_medical.model.BaseModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DosageTimeDto extends BaseModel {
    private UUID timeId;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String description;
}
