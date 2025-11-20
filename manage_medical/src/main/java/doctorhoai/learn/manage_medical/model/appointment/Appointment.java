package doctorhoai.learn.manage_medical.model.appointment;

import doctorhoai.learn.manage_medical.model.BaseModel;
import doctorhoai.learn.manage_medical.model.Payments;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "appointments")
public class Appointment extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID appointmentId;
    @Column(name = "patient_id")
    private UUID patientId;
    private UUID shiftId;
    private UUID serviceId;
    private UUID roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payments payments;
    private double price;
    private String transactionCode;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String fullname;
    private LocalDate dob;
    private Boolean gender;
    private String address;
    @Column(name = "insurance_code")
    private String insuranceCode;
    @Column(name = "emergency_contact")
    private String emergencyContact;
    @Column(name = "citizen_id")
    private String citizenId;
    private String job;
    @Column(name = "phone_number")
    private String phoneNumber;
}
