package doctorhoai.learn.manage_medical.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShiftCount {
    private LocalDate startDate;
    private LocalDate endDate;
}
