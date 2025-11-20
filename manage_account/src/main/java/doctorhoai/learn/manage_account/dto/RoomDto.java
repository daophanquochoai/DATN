package doctorhoai.learn.manage_account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID roomId;
    private String name;
    private String location;
    private List<EmployeeDto> employeeDtos;
}
