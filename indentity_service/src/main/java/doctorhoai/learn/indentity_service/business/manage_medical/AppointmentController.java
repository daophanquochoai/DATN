package doctorhoai.learn.indentity_service.business.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.AppointmentCreate;
import doctorhoai.learn.indentity_service.feign.dto.medical.AppointmentFilter;
import doctorhoai.learn.indentity_service.feign.manage_medical.AppointmentFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentFeign appointmentFeign;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    public ResponseEntity<ResponseObject> createAppointment(
            @Valid @RequestBody AppointmentCreate appointmentCreate
    )
    {
        return appointmentFeign.createAppointment(appointmentCreate);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    public ResponseEntity<ResponseObject> getAppointmentList(
            @RequestBody AppointmentFilter filter
    )
    {
        return appointmentFeign.getAppointmentList(filter);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    public ResponseEntity<ResponseObject> getAppointmentById(
            @PathVariable UUID id
    )
    {
        return appointmentFeign.getAppointmentById(id);
    }

    @GetMapping("/follow_up_visits" )
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseObject> getFollowUpVisits(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        return appointmentFeign.getFollowUpVisits(startDate, endDate);
    }

    @GetMapping("/count-by-service")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseObject> getAppointmentCountByServiceId(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        return appointmentFeign.getAppointmentCountByServiceId(startDate, endDate);
    }

    @GetMapping("/count-by-day")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponseObject> getAppointmentCountByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    )
    {
        return appointmentFeign.getAppointmentCountByDay(startDate, endDate);
    }

    @GetMapping("/old-appointment")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getOldAppointment(
            @RequestParam UUID employeeId,
            @RequestParam String icd10Id
    ) throws ExecutionException, InterruptedException {
        return appointmentFeign.getOldAppointment(employeeId, icd10Id);
    }
}
