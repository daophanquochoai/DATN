package doctorhoai.learn.manage_medical.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dosage_times")
public class DosageTimes extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID timeId;
    private String name;
    private String description;
}
