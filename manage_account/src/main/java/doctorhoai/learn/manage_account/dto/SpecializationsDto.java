package doctorhoai.learn.manage_account.dto;

import doctorhoai.learn.manage_account.dto.groupvalidate.CreateEmployee;
import doctorhoai.learn.manage_account.dto.groupvalidate.UpdateEmployee;
import jakarta.validation.constraints.NotBlank;
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
public class SpecializationsDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "Specialization can't empty", groups = {UpdateEmployee.class, CreateEmployee.class})
    private UUID specializationId;
    @NotNull(message = "Name can't empty")
    @NotBlank(message = "Name can't blank")
    private String name;
    private String description;

    private List<EmployeeDto> employeeDtos;
}
