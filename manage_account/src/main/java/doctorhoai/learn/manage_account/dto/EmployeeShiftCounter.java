package doctorhoai.learn.manage_account.dto;

import doctorhoai.learn.manage_account.model.Employees;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeShiftCounter {
    private Employees employees;
    private Long shiftCount;

    public EmployeeShiftCounter(Employees employees, Long shiftCount) {
        this.employees = employees;
        this.shiftCount = shiftCount;
    }
    public EmployeeShiftCounter() {
    }
}
