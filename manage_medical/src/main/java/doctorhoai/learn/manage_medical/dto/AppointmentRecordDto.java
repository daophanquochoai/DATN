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
public class AppointmentRecordDto {
    private UUID recordId;
    private AppointmentDto appointment;
    private int height;
    private int weight;
    private float bloodPressure;
    private float temperature;
    private float heartRate;
    private float spo2;
    private String symptoms;
    private String initialDiagnosis;
    private String finalDiagnosis;
    private String notes;
    private FollowUpVisitDto followUpVisit;
    private ICD10Dto icd10;

    private List<PerscriptionDto> perscriptionDtos;
}
