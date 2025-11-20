package doctorhoai.learn.manage_account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShiftDto implements Serializable {
    private final static long serialVersion = 1L;
    private UUID id;
    private LocalTime startTime;
    private LocalTime endTime;
}
