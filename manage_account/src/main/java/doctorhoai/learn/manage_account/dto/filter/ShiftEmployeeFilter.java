package doctorhoai.learn.manage_account.dto.filter;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShiftEmployeeFilter extends BaseFilter {
    private List<UUID> employeeIds;
    private LocalDate time;
}
