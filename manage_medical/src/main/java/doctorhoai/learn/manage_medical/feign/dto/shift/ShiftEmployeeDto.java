package doctorhoai.learn.manage_medical.feign.dto.shift;

import doctorhoai.learn.manage_medical.feign.dto.employee.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShiftEmployeeDto {
    private UUID id;
    private EmployeeDto employeeDto;
    private Integer patientSlot;
    private LocalDate date;
    private ShiftDto shift;
    private Integer patientSlotBooked;
}
