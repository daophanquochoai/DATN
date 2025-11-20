package doctorhoai.learn.indentity_service.feign.dto;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShiftFilter extends BaseFilter {
    private String search;
}
