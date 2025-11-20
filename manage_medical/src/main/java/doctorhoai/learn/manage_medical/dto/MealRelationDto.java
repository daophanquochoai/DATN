package doctorhoai.learn.manage_medical.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MealRelationDto {
    private UUID relationsId;
    private String name;
    private String description;
}
