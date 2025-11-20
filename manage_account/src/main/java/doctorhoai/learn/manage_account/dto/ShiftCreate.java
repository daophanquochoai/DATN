package doctorhoai.learn.manage_account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShiftCreate {

    private LocalTime endTime;
    private LocalTime startTime;
}
