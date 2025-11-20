package doctorhoai.learn.manage_medical.feign.dto.patient;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import doctorhoai.learn.manage_medical.feign.dto.AccountStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientsFilter extends BaseFilter {
    private List<UUID> ids;
    private AccountStatus isActive;
}
