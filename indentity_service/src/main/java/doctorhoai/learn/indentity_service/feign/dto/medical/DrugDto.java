package doctorhoai.learn.indentity_service.feign.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DrugDto {
    private UUID drugId;
    private String name;
    private String genericName;
    private String description;
    private String packing;
    private String sideEffects;
    private String contraindication;
    private String allergyInfo;
}
