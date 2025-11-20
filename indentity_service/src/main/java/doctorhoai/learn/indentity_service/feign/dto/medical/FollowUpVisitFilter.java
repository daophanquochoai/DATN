package doctorhoai.learn.indentity_service.feign.dto.medical;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FollowUpVisitFilter extends BaseFilter {
    private UUID patient;
}
