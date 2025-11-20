package doctorhoai.learn.manage_account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WeekDaySummaries {
    private Integer groupShift;
    private Long countDays;
    private LocalDateTime createdAt;
}
