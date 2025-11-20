package doctorhoai.learn.indentity_service.business.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.AppointmentRecordCreate;
import doctorhoai.learn.indentity_service.feign.manage_medical.AppointmentRecordFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("appointment_record")
@RequiredArgsConstructor
public class AppointmentRecordController {

    private final AppointmentRecordFeign appointmentRecordFeign;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> createAppointmentRecord(
            @Valid @RequestBody AppointmentRecordCreate dto
    )
    {
        return appointmentRecordFeign.createAppointmentRecord(dto);
    }

    @GetMapping("/get/appointment/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    public ResponseEntity<ResponseObject> getAppointmentRecordById(
            @PathVariable UUID id
    )
    {
        return appointmentRecordFeign.getAppointmentRecordById(id);
    }

}
