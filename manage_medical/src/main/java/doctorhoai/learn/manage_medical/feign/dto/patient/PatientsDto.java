package doctorhoai.learn.manage_medical.feign.dto.patient;

import doctorhoai.learn.manage_medical.feign.dto.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PatientsDto implements Serializable {

    private static final long serialVersionUID = 1L;
    // patient
    private UUID patientId;
    private String fullName;
    private LocalDate dob;
    private boolean gender;
    private String address;
    private String insuranceCode;
    private String emergencyContact;
    private String citizenId;
    private String job;
    // account
    private String phoneNumber;
    private AccountStatus status;

    // role
    private String nameRole;
    private String description;
}
