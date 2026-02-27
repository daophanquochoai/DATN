package doctorhoai.learn.manage_account.model;

import doctorhoai.learn.manage_account.model.Account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "patients")
public class Patients extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "patient_id")
    private UUID patientId;
    @JoinColumn(name = "account_id", unique = true, nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account accountId;
    @Column(name = "fullname", nullable = false, unique = true)
    private String fullName;
    private String email;
    @Column(name = "dob", nullable = false)
    private LocalDate dob;
    @Column(name = "gender", nullable = false)
    private boolean gender;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "insurance_code", unique = true)
    private String insuranceCode;
    @Column(name = "emergency_contact", nullable = false)
    private String emergencyContact;
    @Column(name = "citizen_id")
    private String citizenId;
    @Column(name = "job", nullable = false)
    private String job;
}
