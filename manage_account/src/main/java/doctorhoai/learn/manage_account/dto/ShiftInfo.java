package doctorhoai.learn.manage_account.dto;

import lombok.*;

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
