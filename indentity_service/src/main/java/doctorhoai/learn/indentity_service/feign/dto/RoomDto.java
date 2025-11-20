package doctorhoai.learn.indentity_service.feign.dto;

import doctorhoai.learn.indentity_service.feign.dto.groupvalidate.CreateShift;
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
