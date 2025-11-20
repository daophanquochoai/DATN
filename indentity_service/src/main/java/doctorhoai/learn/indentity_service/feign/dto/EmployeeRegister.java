package doctorhoai.learn.indentity_service.feign.dto;

import doctorhoai.learn.indentity_service.feign.dto.Adult.Adult;
import doctorhoai.learn.indentity_service.feign.dto.groupvalidate.CreateEmployee;
import doctorhoai.learn.indentity_service.feign.dto.groupvalidate.UpdateEmployee;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeRegister {
    // employee
    @NotNull(message = "Full name can't empty", groups = {CreateEmployee.class, UpdateEmployee.class})
    @NotBlank(message = "Full name can't blank", groups = {CreateEmployee.class, UpdateEmployee.class})
    private String fullName;
    @NotNull(message = "Citizen id code can't empty", groups = {CreateEmployee.class, UpdateEmployee.class})
    @NotBlank(message = "Citizen id contact code can't blank", groups = {CreateEmployee.class, UpdateEmployee.class})
    @Pattern(regexp = "^(\\d{9}|\\d{12})$", message = "Citizen ID must be 9 or 12 digits", groups = {CreateEmployee.class, UpdateEmployee.class})
    private String citizenId;
    @NotNull(message = "Date of birth is required", groups = {CreateEmployee.class, UpdateEmployee.class})
    @Past(message = "Date of birth must be in the past", groups = {CreateEmployee.class, UpdateEmployee.class})
    @Adult(groups = {CreateEmployee.class, UpdateEmployee.class})
    private LocalDate dob;
    @NotNull(message = "Gender can't empty", groups = {CreateEmployee.class, UpdateEmployee.class})
    private boolean gender;
    @NotNull(message = "Address can't empty", groups = {CreateEmployee.class, UpdateEmployee.class})
    @NotBlank(message = "Address can't blank", groups = {CreateEmployee.class, UpdateEmployee.class})
    private String address;
    @NotNull(message = "Avatar can't empty", groups = {CreateEmployee.class, UpdateEmployee.class})
    @NotBlank(message = "Avatar can't blank", groups = {CreateEmployee.class, UpdateEmployee.class})
    private String avatar;
    @NotNull(message = "Hired date is required", groups = {CreateEmployee.class, UpdateEmployee.class})
    @Past(message = "Hired date must be in the past", groups = {CreateEmployee.class, UpdateEmployee.class})
    private LocalDate hiredDate;
    @Size(max = 100, message = "Email can't exceed 100 characters", groups = {CreateEmployee.class, UpdateEmployee.class})
    @NotBlank(message = "Email can't be blank", groups = {CreateEmployee.class, UpdateEmployee.class})
    @Email(message = "Email should be valid", groups = {CreateEmployee.class, UpdateEmployee.class})
    private String email;
    // account
    @NotBlank(message = "Phone number can't be blank", groups = {CreateEmployee.class, UpdateEmployee.class})
    @Pattern(regexp = "^(0\\d{9})$", message = "Phone number must be 10 digits and start with 0", groups = {CreateEmployee.class, UpdateEmployee.class})
    private String phoneNumber;
    @NotBlank(message = "Password can't be blank", groups = {CreateEmployee.class})
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character",
            groups = {CreateEmployee.class}
    )
    private String password;
    @NotNull(message = "Status can't empty",groups = {CreateEmployee.class, UpdateEmployee.class})
    private AccountStatus status;
    // role
    @NotNull(message = "Role can't empty", groups = {CreateEmployee.class, UpdateEmployee.class})
    private UUID roleId;
    // specialization
    @NotNull(message = "Specialization is required", groups = {CreateEmployee.class, UpdateEmployee.class})
    private UUID specialization;
    @NotNull(message = "Room is required", groups = {CreateEmployee.class, UpdateEmployee.class})
    private UUID room;
    @NotNull(message = "Service is required", groups = {CreateEmployee.class, UpdateEmployee.class})
    private List<UUID> services;
    private String profile;
}
