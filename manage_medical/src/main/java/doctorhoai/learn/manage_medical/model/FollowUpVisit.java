package doctorhoai.learn.manage_medical.model;

import doctorhoai.learn.manage_medical.model.appointment.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "follow_up_visit")
public class FollowUpVisit extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "follow_up_id")
    private UUID followUpId;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_record_id")
    private AppointmentRecord appointmentRecord;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private LocalDate followUpDate;
    private String instruction;
}
