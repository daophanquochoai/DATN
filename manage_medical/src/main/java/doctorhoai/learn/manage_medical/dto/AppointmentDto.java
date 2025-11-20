package doctorhoai.learn.manage_medical.dto;

import doctorhoai.learn.manage_medical.feign.dto.employee.RoomDto;
import doctorhoai.learn.manage_medical.feign.dto.patient.PatientsDto;
import doctorhoai.learn.manage_medical.feign.dto.service.ServiceDto;
import doctorhoai.learn.manage_medical.feign.dto.shift.ShiftEmployeeDto;
import doctorhoai.learn.manage_medical.model.appointment.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentDto implements Serializable {

    private static long seriableVersion = 1L;

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
