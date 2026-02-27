package doctorhoai.learn.manage_medical.service.appointment;

import doctorhoai.learn.manage_medical.dto.AppointmentRecordDto;
import doctorhoai.learn.manage_medical.dto.request.AppointmentCreate;
import doctorhoai.learn.manage_medical.dto.AppointmentDto;
import doctorhoai.learn.manage_medical.dto.filter.AppointmentFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;
import doctorhoai.learn.manage_medical.dto.request.ShiftCount;
import doctorhoai.learn.manage_medical.feign.dto.employee.EmployeeDto;
import doctorhoai.learn.manage_medical.feign.dto.shift.ShiftEmployeeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface AppointmentService {
    AppointmentDto createAppointment(AppointmentCreate dto);
    PageObject getAppointment(AppointmentFilter filter);
    AppointmentDto getAppointmentById(UUID id) throws ExecutionException, InterruptedException;
    void paymentAppointment(UUID appointment, String transactionNo);
    List<EmployeeDto> checkAppointmentWasBook(LocalDate date, UUID serviceId, UUID patientId);
    Map<String, Long> getAppointmentCountByShiftIds(Set<UUID> shiftIds);
    List<AppointmentDto> getAppointmentByPatientAndDateAndServiceAndEmployee(UUID patientId, UUID serviceId, List<ShiftEmployeeDto> shifts);
    Map<UUID, Map<String, Object>> getAppointmentCountByServiceId(LocalDate startDate, LocalDate endDate);
    Map<String, Integer> getAppointmentStatusCount(LocalDate startDate, LocalDate endDate);
    AppointmentRecordDto getOldAppointment(UUID employeeId, String icd10Id) throws ExecutionException, InterruptedException;
    List<AppointmentDto> getAppointmentByShiftId(UUID shiftId);
    List<UUID> countShiftInAppointments(ShiftCount filter);
}
