package doctorhoai.learn.manage_account.feign.appointment;

import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.ShiftEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@FeignClient(
        name = "medical",
        contextId = "accountAppointment",
        path = "/appointment",
        fallbackFactory = AppointmentFeignFallback.class
)
public interface AppointmentFeign {
    @GetMapping("/check/booked/{patientId}/{serviceId}")
    ResponseEntity<ResponseObject> checkAppointmentBooked(
            @RequestParam LocalDate date,
            @PathVariable UUID patientId,
            @PathVariable UUID serviceId
    );
    @GetMapping("/count-by-shift-ids")
    ResponseEntity<ResponseObject> getAppointmentCountByShiftIds(
            @RequestParam Set<UUID> shiftIds
    );
    @GetMapping("/get-by-patient-service-and-shifts")
    ResponseEntity<ResponseObject> getAppointmentByPatientAndDateAndServiceAndShift(
            @RequestParam UUID patientId,
            @RequestParam UUID serviceId,
            @RequestParam List<ShiftEmployeeDto> shiftIds
    );

    @GetMapping("/shift-employee/{id}")
    ResponseEntity<ResponseObject> getAppointmentByShiftId(
            @PathVariable UUID id
    );

    @DeleteMapping("/shift-employee/{id}")
    ResponseEntity<ResponseObject> removeAppointmentByShiftId(
            @PathVariable UUID id
    );
}
