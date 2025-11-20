package doctorhoai.learn.indentity_service.feign.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FollowUpVisitDto {
    private UUID followUpId;
    private AppointmentRecordDto appointmentRecord;
    private AppointmentDto appointment;
    private LocalDate followUpDate;
    private String instruction;
}
