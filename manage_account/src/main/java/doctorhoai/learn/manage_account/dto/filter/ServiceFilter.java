package doctorhoai.learn.manage_account.dto.filter;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceFilter extends BaseFilter {
    private Double startPrice;
    private Double endPrice;
    private List<UUID> serviceIds;
}
