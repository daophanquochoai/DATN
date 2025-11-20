package doctorhoai.learn.indentity_service.feign.dto.medical;

import doctorhoai.learn.indentity_service.feign.dto.PatientsDto;
import doctorhoai.learn.indentity_service.feign.dto.RoomDto;
import doctorhoai.learn.indentity_service.feign.dto.ServiceDto;
import doctorhoai.learn.indentity_service.feign.dto.ShiftEmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentDto {
    private UUID appointmentId;
    private PatientsDto patientId;
    private ShiftEmployeeDto shiftId;
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
