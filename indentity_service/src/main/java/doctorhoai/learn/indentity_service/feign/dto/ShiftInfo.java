package doctorhoai.learn.indentity_service.feign.dto;

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
public class ShiftInfo {
    private UUID id;
    private Integer patientSlot;
    private LocalDate date;
    private ShiftDto shiftDto;
}
