package doctorhoai.learn.manage_medical.feign.feign.patient;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.feign.dto.AccountStatus;
import doctorhoai.learn.manage_medical.feign.dto.patient.PatientsFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "medicalPatient",
        path = "/patient",
        fallbackFactory = PatientFeignFallBack.class
)
public interface PatientFeign {
    @GetMapping("/get/patient/{id}")
    ResponseEntity<ResponseObject> getPatientsById(
            @PathVariable UUID id,
            @RequestParam(required = false) AccountStatus status
    );
    @PostMapping("/list/id")
    ResponseEntity<ResponseObject> getPatientByIds(
            @RequestBody PatientsFilter filter
    );
    @GetMapping("/all")
    ResponseEntity<ResponseObject> getAllPatients(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<UUID> ids
    );
}
