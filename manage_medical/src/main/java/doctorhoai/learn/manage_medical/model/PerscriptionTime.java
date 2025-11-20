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
@Table(name = "perscription_times")
public class PerscriptionTime extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID perscriptionTimesId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id")
    private DosageTimes timeId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "perscription_id")
    private Perscriptions perscriptionId;
}
