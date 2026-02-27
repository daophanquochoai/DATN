package doctorhoai.learn.indentity_service.business.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.ShiftEmployeeCreate;
import doctorhoai.learn.indentity_service.feign.dto.ShiftEmployeeFilter;
import doctorhoai.learn.indentity_service.feign.dto.ShiftFilter;
import doctorhoai.learn.indentity_service.feign.manage_account.ShiftFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("shift")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftFeign shiftFeign;


    @PostMapping("list")
    ResponseEntity<ResponseObject> getShiftList(
            @RequestBody ShiftFilter filter
    )
    {
        return shiftFeign.getShiftList(filter);
    }

    @PostMapping("/create/employee")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    ResponseEntity<ResponseObject> createShiftEmployee(
            @RequestBody ShiftEmployeeCreate create
    )
    {
        return shiftFeign.createShiftEmployee(create);
    }

    @PostMapping("/get/employee")
    ResponseEntity<ResponseObject> getShiftEmployee(
            @RequestBody ShiftEmployeeFilter filter
    )
    {
        return shiftFeign.getShiftEmployee(filter);
    }

    @GetMapping("/get/employee/{id}")
    ResponseEntity<ResponseObject> getShiftEmployeeById(
            @PathVariable UUID id
    )
    {
        return shiftFeign.getShiftEmployeeById(id);
    }

    @GetMapping("/get/employees")
    ResponseEntity<ResponseObject> getShiftEmployeeByIds(
            @RequestParam(required = false) List<UUID> ids
    )
    {
        return shiftFeign.getShiftEmployeeByIds(ids);
    }

    @GetMapping("/get/employees/employee")
    ResponseEntity<ResponseObject> getShiftEmployeeByEmployeeIds(
            @RequestParam(required = false) List<UUID> ids
    )
    {
        return shiftFeign.getShiftEmployeeByEmployeeIds(ids);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> deleteShift(
            @PathVariable UUID id
    ){
        return shiftFeign.deleteShift(id);
    }
}
