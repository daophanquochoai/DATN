package doctorhoai.learn.manage_account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeekDayShiftCreate {
    private int dayOfWeek;
    private List<UUID> shiftIds;
}
