package doctorhoai.learn.indentity_service.feign.dto.medical;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DosageTimeFilter extends BaseFilter {
    private List<UUID> ids;
}
