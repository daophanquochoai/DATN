package doctorhoai.learn.indentity_service.feign.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PerscriptionCreate {
    private UUID perscriptionId;
    private UUID recordId;
    private UUID drugId;
    private String customDrugName;
    private int dosage;
    private String frequency;
    private int duration;
    private UUID unitDosageId;
    private String instructions;
    private UUID mealRelation;
    private List<UUID> dosageTimeDtos;
}
