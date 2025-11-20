package doctorhoai.learn.indentity_service.feign.dto;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeesFilter extends BaseFilter {
    private AccountStatus isActive;
    private List<UUID> ids;
    private List<UUID> specializations;
    private List<UUID> rooms;
    private List<UUID> services;
}
