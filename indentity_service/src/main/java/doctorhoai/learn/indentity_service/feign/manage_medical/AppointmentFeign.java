package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.medical.AppointmentCreate;
import doctorhoai.learn.indentity_service.feign.dto.medical.AppointmentFilter;
import doctorhoai.learn.indentity_service.feign.manage_account.EmployeeFeignFallBack;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@FeignClient(
        name = "medical",
        contextId = "authAppointment",
        path = "/appointment",
        fallbackFactory = AppointmentFeignFallback.class,
        configuration = FeignConfig.class
)
public interface AppointmentFeign {
    @PostMapping("/create")
    ResponseEntity<ResponseObject> createAppointment(
            @Valid @RequestBody AppointmentCreate appointmentCreate
    );

    @PostMapping("/list")
    ResponseEntity<ResponseObject> getAppointmentList(
            @RequestBody AppointmentFilter filter
    );

    @GetMapping("/get/{id}")
    ResponseEntity<ResponseObject> getAppointmentById(
            @PathVariable UUID id
    );
    @GetMapping("/follow_up_visits" )
    ResponseEntity<ResponseObject> getFollowUpVisits(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );
    @GetMapping("/count-by-service")
    ResponseEntity<ResponseObject> getAppointmentCountByServiceId(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );
    @GetMapping("/count-by-day")
    ResponseEntity<ResponseObject> getAppointmentCountByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );
    @GetMapping("/old-appointment")
    ResponseEntity<ResponseObject> getOldAppointment(
            @RequestParam UUID employeeId,
            @RequestParam String icd10Id
    ) throws ExecutionException, InterruptedException;
}
