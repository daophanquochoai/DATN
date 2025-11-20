package doctorhoai.learn.indentity_service.feign.dto;

import doctorhoai.learn.indentity_service.feign.dto.Adult.Adult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientsRegister {
    // patients
    @NotNull(message = "Full name can't empty", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Full name can't blank", groups = {CreatePatient.class, UpdatePatient.class})
    private String fullName;
    @NotNull(message = "Date of birth is required", groups = {CreatePatient.class, UpdatePatient.class})
    @Past(message = "Date of birth must be in the past", groups = {CreatePatient.class, UpdatePatient.class})
    @Adult
    private LocalDate dob;
    @NotNull(message = "Gender can't empty", groups = {CreatePatient.class, UpdatePatient.class})
    private boolean gender;
    @NotNull(message = "Address can't empty", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Address can't blank", groups = {CreatePatient.class, UpdatePatient.class})
    private String address;
    @NotNull(message = "Insurance code can't empty", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Insurance code can't blank", groups = {CreatePatient.class, UpdatePatient.class})
    private String insuranceCode;
    @NotNull(message = "Emergency contact code can't empty", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Emergency contact code can't blank", groups = {CreatePatient.class, UpdatePatient.class})
    private String emergencyContact;
    @NotNull(message = "Citizen id code can't empty", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Citizen id contact code can't blank", groups = {CreatePatient.class, UpdatePatient.class})
    @Pattern(regexp = "^(\\d{9}|\\d{12})$", message = "Citizen ID must be 9 or 12 digits", groups = {CreatePatient.class, UpdatePatient.class})
    private String citizenId;
    @NotNull(message = "Job code can't empty", groups = {CreatePatient.class, UpdatePatient.class})
    @NotBlank(message = "Job contact code can't blank", groups = {CreatePatient.class, UpdatePatient.class})
    private String job;
    //account
    @NotBlank(message = "Phone number can't be blank", groups = {CreatePatient.class, UpdatePatient.class})
    @Pattern(regexp = "^(0\\d{9})$", message = "Phone number must be 10 digits and start with 0", groups = {CreatePatient.class, UpdatePatient.class})
    private String phoneNumber;
    @NotBlank(message = "Password can't be blank", groups = {CreatePatient.class})
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character",
            groups = {CreatePatient.class}
    )
    private String password;
    @NotNull(message = "Status can't empty", groups = {CreatePatient.class, UpdatePatient.class})
    private AccountStatus status;
    // role
    @NotNull(message = "Role can't empty", groups = {CreatePatient.class, UpdatePatient.class})
    private UUID roleId;
}
