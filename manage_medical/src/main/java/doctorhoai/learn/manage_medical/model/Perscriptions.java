package doctorhoai.learn.manage_medical.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "perscriptions")
public class Perscriptions extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID perscriptionId;
    @JoinColumn(name = "record_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AppointmentRecord recordId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "drug_id")
    private Drug drugId;
    private String customDrugName;
    private int dosage;
    private String frequency;
    private int duration;
    @JoinColumn(name = "unit_dosage_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Units unitDosageId;
    private String instructions;
    @JoinColumn(name = "meal_relation_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MealRelation mealRelation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "perscriptionId")
    private List<PerscriptionTime> perscriptionTimes;
}
