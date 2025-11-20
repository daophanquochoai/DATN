package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.medical.AppointmentRecordCreate;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(
        name = "medical",
        contextId = "authAppointmentRecord",
        path = "/appointment_record",
        fallbackFactory = AppointmentRecordFeignFallback.class,
        configuration = FeignConfig.class
)
public interface AppointmentRecordFeign {
    @PostMapping("/create")
    ResponseEntity<ResponseObject> createAppointmentRecord(
            @Valid @RequestBody AppointmentRecordCreate dto
    );

    @GetMapping("/get/appointment/{id}")
    ResponseEntity<ResponseObject> getAppointmentRecordById(
            @PathVariable UUID id
    );
}
