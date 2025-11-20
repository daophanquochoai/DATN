package doctorhoai.learn.manage_medical.feign.dto.employee;

import doctorhoai.learn.manage_medical.feign.dto.AccountStatus;
import doctorhoai.learn.manage_medical.feign.dto.service.ServiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto implements Serializable {
    private static final long serialVersionUID = 1L;
    // employee
    private UUID employeeId;
    private String fullName;
    private String citizenId;
    private LocalDate dob;
    private boolean gender;
    private String address;
    private String avatar;
    private LocalDate hiredDate;
    private String email;
    private String profile;
    // account
    private UUID accountId;
    private String phoneNumber;
    private AccountStatus status;
    // role
    private String nameRole;
    private String description;
    // department
    private RoomDto roomDto;
    // specialization
    private SpecializationsDto specialization;
    //service
    private List<ServiceDto> serviceDto;
}
