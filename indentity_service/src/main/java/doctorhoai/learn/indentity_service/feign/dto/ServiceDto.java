package doctorhoai.learn.indentity_service.feign.dto;

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
public class ServiceDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID serviceId;
    private String name;
    private String description;
    private double price;
    private String image;
    private List<EmployeeDto> employeeDtos;
}
