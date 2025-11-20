package doctorhoai.learn.manage_medical.feign.feign.employee;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.feign.dto.AccountStatus;
import doctorhoai.learn.manage_medical.feign.dto.employee.EmployeesFilter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "medicalEmployee",
        path = "/employee",
        fallbackFactory = EmployeeFeignFallBack.class
)
public interface EmployeeFeign {

    @GetMapping("/get/employee/{id}")
    ResponseEntity<ResponseObject> getAccountEmployee(
            @Valid @PathVariable UUID id,
            @RequestParam(required = false) AccountStatus active
    );
    @PostMapping("/list/id")
    ResponseEntity<ResponseObject> getEmployeeByIds(
            @RequestBody EmployeesFilter filter
    );
}
