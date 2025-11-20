package doctorhoai.learn.manage_account.feign.dto;

import doctorhoai.learn.manage_account.dto.EmployeeDto;
import doctorhoai.learn.manage_account.dto.PatientsDto;
import doctorhoai.learn.manage_account.dto.RoomDto;
import doctorhoai.learn.manage_account.dto.ServiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentDto {
    private UUID appointmentId;
    private PatientsDto patientId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private EmployeeDto employeeId;
    private ServiceDto serviceId;
    private PaymentDto payments;
    private RoomDto roomDto;
    private double price;
    private String transactionCode;
    private AppointmentStatus status;
    private String fullname;
    private LocalDate dob;
    private Boolean gender;
    private String address;
    private String insuranceCode;
    private String emergencyContact;
    private String citizenId;
    private String job;
    private String phoneNumber;
}
