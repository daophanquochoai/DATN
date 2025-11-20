package doctorhoai.learn.manage_account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WeekGroupDto {
    private Integer id;
    private EmployeeDto employeeDto;
    private Integer count;
    private List<WeekDayDto> weekDayDtos;
}
