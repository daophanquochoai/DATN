package doctorhoai.learn.manage_account.controller;

import doctorhoai.learn.manage_account.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.ShiftEmployeeCreate;
import doctorhoai.learn.manage_account.dto.filter.ShiftEmployeeFilter;
import doctorhoai.learn.manage_account.dto.filter.ShiftFilter;
import doctorhoai.learn.manage_account.service.shift.ShiftEmployeeService;
import doctorhoai.learn.manage_account.service.shift.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("shift")
@RequiredArgsConstructor
public class ShiftController {
    private final ShiftService shiftService;
    private final ShiftEmployeeService shiftEmployeeService;

    @PostMapping("list")
    public ResponseEntity<ResponseObject> getShiftList(
            @RequestBody ShiftFilter filter
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(shiftService.getShift(filter))
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @PostMapping("/create/employee")
    public ResponseEntity<ResponseObject> createShiftEmployee(
            @RequestBody ShiftEmployeeCreate create
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(shiftEmployeeService.createShiftEmployee(create))
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @PostMapping("/get/employee")
    public ResponseEntity<ResponseObject> getShiftEmployee(
        @RequestBody ShiftEmployeeFilter filter
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(shiftEmployeeService.getShiftEmployee(filter))
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @GetMapping("/get/employee/{id}")
    public ResponseEntity<ResponseObject> getShiftEmployeeById(
            @PathVariable UUID id

            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(shiftEmployeeService.getShiftEmployee(id))
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @GetMapping("/get/employees")
    public ResponseEntity<ResponseObject> getShiftEmployeeByIds(
            @RequestParam(required = false) List<UUID> ids
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(shiftEmployeeService.getShiftEmployeeByIds(ids))
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @GetMapping("/get/employees/employee")
    public ResponseEntity<ResponseObject> getShiftEmployeeByEmployeeIds(
            @RequestParam(required = false) List<UUID> ids,
            @RequestParam(required = false) LocalDate date
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(shiftEmployeeService.getShiftEmployeeByEmployeeIds(ids, date))
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @GetMapping("/get/employees/inmonth/{id}")
    public ResponseEntity<ResponseObject> getShiftEmployeeByEmployeeId(
            @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(shiftEmployeeService.getShiftEmployeeByEmployeeId(id))
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteShift(
            @PathVariable UUID id
    )
    {
        shiftEmployeeService.removeShiftIfNotBook(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.DELETE_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }
}
