package doctorhoai.learn.indentity_service.feign.dto;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WeekDayFilter extends BaseFilter {
    private List<UUID> employeeIds;
}
