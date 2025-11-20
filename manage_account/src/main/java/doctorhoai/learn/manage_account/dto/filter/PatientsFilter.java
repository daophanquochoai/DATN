package doctorhoai.learn.manage_account.dto.filter;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import doctorhoai.learn.manage_account.model.Account.AccountStatus;
import lombok.*;

import java.time.LocalDate;
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
