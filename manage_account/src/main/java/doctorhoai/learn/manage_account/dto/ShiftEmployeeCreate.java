package doctorhoai.learn.manage_account.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShiftEmployeeCreate {
    private UUID employeeId;
    private List<ShiftInfo> shiftIds;
}

