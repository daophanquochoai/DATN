package doctorhoai.learn.manage_medical.dto.filter;

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
