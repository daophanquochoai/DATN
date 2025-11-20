package doctorhoai.learn.manage_account.model.notification;

import doctorhoai.learn.manage_account.model.BaseModel;
import doctorhoai.learn.manage_account.model.Patients;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "notifications")
public class Notifications extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID notificationId;
    private String message;
    @Column(name = "record_type")
    @Enumerated(EnumType.STRING)
    private RecordType recordType;
    @Column(name = "is_read")
    private boolean isRead;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patients patientId;
    @Column(name = "appointment_id")
    private UUID appointmentId;
}
