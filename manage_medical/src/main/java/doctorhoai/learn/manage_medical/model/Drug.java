package doctorhoai.learn.manage_medical.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "drugs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Drug extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "drug_id")
    private UUID drugId;
    private String name;
    private String genericName;
    private String description;
    private String packing;
    private String sideEffects;
    @Column(name = "contraindications")
    private String contraindication;
    @Column(name = "allergy_info")
    private String allergyInfo;
}
