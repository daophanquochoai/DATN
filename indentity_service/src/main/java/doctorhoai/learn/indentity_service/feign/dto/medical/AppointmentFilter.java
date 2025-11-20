package doctorhoai.learn.indentity_service.feign.dto.medical;

import doctorhoai.learn.base_domain.dto.BaseFilter;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentFilter extends BaseFilter implements Serializable {
    private static final long serialVersionId = 1L;
    private List<UUID> employeeId;
    private List<UUID> patientId;
    private List<AppointmentStatus> statuses;
    private LocalTime startTime;
    private LocalTime endTime;
}
