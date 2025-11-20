package doctorhoai.learn.manage_medical.dto;

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
public class PerscriptionDto {
    private UUID perscriptionId;
    private AppointmentDto recordId;
    private DrugDto drugId;
    private String customDrugName;
    private int dosage;
    private String frequency;
    private int duration;
    private UnitsDto unitDosageId;
    private String instructions;
    private MealRelationDto mealRelation;
    private List<DosageTimeDto> dosageTimeDtos;
}
