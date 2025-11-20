package doctorhoai.learn.indentity_service.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

