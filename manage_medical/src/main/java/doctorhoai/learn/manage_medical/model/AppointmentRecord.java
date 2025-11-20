package doctorhoai.learn.manage_medical.model;

import doctorhoai.learn.manage_medical.model.appointment.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "appointment_records")
public class AppointmentRecord extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "record_id")
    private UUID recordId;
    @JoinColumn(name = "appointment_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Appointment appointment;
    private int height;
    private int weight;
    @Column(name = "blood_pressure")
    private float bloodPressure;
    private float temperature;
    @Column(name = "heart_rate")
    private float heartRate;
    private float spo2;
    private String symptoms;
    @Column(name = "initial_diagnosis")
    private String initialDiagnosis;
    @Column(name = "final_diagnosis")
    private String finalDiagnosis;
    private String notes;

    @JoinColumn(name = "icd10")
    @ManyToOne(cascade = CascadeType.ALL)
    private ICD10Codes icd10Codes;

    @JoinColumn(name = "follow_up_visit")
    @OneToOne(mappedBy = "appointmentRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FollowUpVisit followUpVisit;

    @OneToMany(mappedBy = "recordId", cascade = CascadeType.ALL)
    private List<Perscriptions> perscriptions;
}
