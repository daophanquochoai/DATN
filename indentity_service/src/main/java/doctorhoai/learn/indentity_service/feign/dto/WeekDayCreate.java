package doctorhoai.learn.indentity_service.feign.dto;

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
public class WeekDayCreate {
    private UUID employeeId;
    private List<WeekDayShiftCreate> weekDays;
}
