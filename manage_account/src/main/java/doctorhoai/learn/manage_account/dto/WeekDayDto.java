package doctorhoai.learn.manage_account.dto;

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
public class WeekDayDto {
    private Integer dayOfWeek;
    private UUID id;
    private List<ShiftDto> shiftDtos;
}
