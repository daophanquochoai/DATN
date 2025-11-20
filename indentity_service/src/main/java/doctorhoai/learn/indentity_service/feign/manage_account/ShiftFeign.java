package doctorhoai.learn.indentity_service.feign.manage_account;


import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.ShiftEmployeeCreate;
import doctorhoai.learn.indentity_service.feign.dto.ShiftEmployeeFilter;
import doctorhoai.learn.indentity_service.feign.dto.ShiftFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "authShift",
        path = "/shift",
        fallbackFactory = ShiftFeignFallback.class,
        configuration = FeignConfig.class
)
public interface ShiftFeign {

    @PostMapping("list")
    ResponseEntity<ResponseObject> getShiftList(
            @RequestBody ShiftFilter filter
    );

    @PostMapping("/create/employee")
    ResponseEntity<ResponseObject> createShiftEmployee(
            @RequestBody ShiftEmployeeCreate create
    );

    @PostMapping("/get/employee")
    ResponseEntity<ResponseObject> getShiftEmployee(
            @RequestBody ShiftEmployeeFilter filter
    );

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
            @RequestParam(required = false) List<UUID> ids
    );
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteShift(
            @PathVariable UUID id
    );
}
