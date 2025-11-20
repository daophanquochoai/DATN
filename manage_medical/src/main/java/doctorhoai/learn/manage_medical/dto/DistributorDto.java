package doctorhoai.learn.manage_medical.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistributorDto {
    private UUID distributorId;
    private String name;
    private String description;
}
