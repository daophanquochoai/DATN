package doctorhoai.learn.manage_account.mapper;

import doctorhoai.learn.manage_account.dto.*;
import doctorhoai.learn.manage_account.model.*;
import doctorhoai.learn.manage_account.model.notification.Notifications;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Mapper {

    /**
     * convert from dto to class
     * @param dto
     * @return
     */
    public Specializations convertToSpecializations(SpecializationsDto dto) {
        return Specializations.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    /**
     * convert from class to dto
     * @param cl
     * @return
     */
    public SpecializationsDto convertToSpecializationsDto(Specializations cl) {
        return SpecializationsDto.builder()
                .specializationId(cl.getSpecializationId())
                .name(cl.getName())
                .description(cl.getDescription())
                .build();
    }

    public Services convertToServices(ServiceDto dto) {
        return Services.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .image(dto.getImage())
                .build();
    }

    public ServiceDto convertToServiceDto(Services cl) {
        return ServiceDto.builder()
                .serviceId(cl.getServiceId())
                .name(cl.getName())
                .description(cl.getDescription())
                .price(cl.getPrice())
                .image(cl.getImage())
                .build();
    }

    public NotificationDto convertToNotificationDto(Notifications cl) {
        return NotificationDto.builder()
                .notificationId(cl.getNotificationId())
                .message(cl.getMessage())
                .recordType(cl.getRecordType())
                .isRead(cl.isRead())
                .patientsDto(convertPatientDto(cl.getPatientId()))
                .appointmentId(cl.getAppointmentId())
                .build();
    }

    public Notifications convertToNotification(NotificationDto dto) {
        return Notifications.builder()
                .message(dto.getMessage())
                .recordType(dto.getRecordType())
                .isRead(dto.isRead())
                .build();
    }
    /**
     * convert cl to dto
     * @param cl - employee
     * @return - employee_dto
     */
    public EmployeeDto convertToEmployeeDto(Employees cl) {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .employeeId(cl.getEmployeeId())
                .fullName(cl.getFullName())
                .citizenId(cl.getCitizenId())
                .dob(cl.getDob())
                .gender(cl.isGender())
                .address(cl.getAddress())
                .avatar(cl.getAvatar())
                .hiredDate(cl.getHiredDate())
                .email(cl.getEmail())
                .profile(cl.getProfile())
                //account
                .accountId(cl.getAccountId().getAccountId())
                .phoneNumber(cl.getAccountId().getPhoneNumber())
                .status(cl.getAccountId().getStatus())
                //role
                .nameRole(cl.getAccountId().getRoleId().getNameRole())
                .description(cl.getAccountId().getRoleId().getDescription())
                //specialization
                .specialization(convertToSpecializationsDto(cl.getSpecializationId()))
                .roomDto(convertToRoomDto(cl.getRoomId()))
                .build();
        List<ServiceDto> serviceDtos = new ArrayList<>();
        for( EmployeeServices es : cl.getEmployeeServices()){
            serviceDtos.add(convertToServiceDto(es.getServices()));
        }
        employeeDto.setServiceDto(serviceDtos);
        return employeeDto;
    }

    /**
     * convert from class to dto
     * @param cl - patient class
     * @return - patient dto
     */
    public PatientsDto convertPatientDto(Patients cl){
        return PatientsDto.builder()
                .patientId(cl.getPatientId())
                .fullName(cl.getFullName())
                .dob(cl.getDob())
                .gender(cl.isGender())
                .address(cl.getAddress())
                .insuranceCode(cl.getInsuranceCode())
                .emergencyContact(cl.getEmergencyContact())
                .citizenId(cl.getCitizenId())
                .job(cl.getJob())
                .phoneNumber(cl.getAccountId().getPhoneNumber())
                .status(cl.getAccountId().getStatus())
                .nameRole(cl.getAccountId().getRoleId().getNameRole())
                .description(cl.getAccountId().getRoleId().getDescription())
                .build();
    }

    /**
     * convert from patient dto to patient class
     * @param dto - patient dto
     * @return - patient class
     */
    public Patients convertToPatient(PatientsDto dto) {
        return Patients.builder()
                .fullName(dto.getFullName())
                .dob(dto.getDob())
                .gender(dto.isGender())
                .address(dto.getAddress())
                .insuranceCode(dto.getInsuranceCode())
                .emergencyContact(dto.getEmergencyContact())
                .citizenId(dto.getCitizenId())
                .job(dto.getJob())
                .build();
    }

    public RoomDto convertToRoomDto( Rooms rooms){
        RoomDto roomDto = RoomDto.builder()
                .roomId(rooms.getRoomId())
                .name(rooms.getName())
                .location(rooms.getLocation())
                .build();
        return roomDto;
    }

    public Rooms convertToRoom(RoomDto roomDto){
        return Rooms.builder()
                .name(roomDto.getName())
                .location(roomDto.getLocation())
                .build();
    }

    public ShiftDto convertToShiftDto(Shift shift){
        return ShiftDto.builder()
                .id(shift.getId())
                .endTime(shift.getEndTime())
                .startTime(shift.getStartTime())
                .build();
    }

}
