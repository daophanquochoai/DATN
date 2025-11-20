package doctorhoai.learn.manage_medical.feign.feign.shift;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.feign.feign.patient.PatientFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "medicalShift",
        path = "/shift",
        fallbackFactory = ShiftFeignFallback.class
)
public interface ShiftFeign {
    @GetMapping("/get/employee/{id}")
    ResponseEntity<ResponseObject> getShiftEmployeeById(
            @PathVariable UUID id
    );
    @GetMapping("/get/employees")
    ResponseEntity<ResponseObject> getShiftEmployeeByIds(
            @RequestParam(required = false) List<UUID> ids
    );
    @GetMapping("/get/employees/employee")
    ResponseEntity<ResponseObject> getShiftEmployeeByEmployeeIds(
            @RequestParam(required = false) List<UUID> ids,
            @RequestParam(required = false) LocalDate date
    );
    @GetMapping("/get/employees/inmonth/{id}")
    public ResponseEntity<ResponseObject> getShiftEmployeeByEmployeeId(
            @PathVariable UUID id
    );
}
