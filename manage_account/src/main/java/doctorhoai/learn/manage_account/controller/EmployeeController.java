package doctorhoai.learn.manage_account.controller;

import doctorhoai.learn.manage_account.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_account.dto.EmployeeRegister;
import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.UpdatePassword;
import doctorhoai.learn.manage_account.dto.filter.EmployeesFilter;
import doctorhoai.learn.manage_account.dto.groupvalidate.CreateEmployee;
import doctorhoai.learn.manage_account.dto.groupvalidate.UpdateEmployee;
import doctorhoai.learn.manage_account.model.Account.AccountStatus;
import doctorhoai.learn.manage_account.service.employee.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     *  register account
     * @param register - data of employee
     * @return - data after register
     */
    @PostMapping("/register-employee")
    public ResponseEntity<ResponseObject> createAccountEmployee(
            @Validated(CreateEmployee.class) @RequestBody EmployeeRegister register
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.SAVE_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.createEmployee(register))
                        .build()
        );
    }

    /**
     * get employee by id and status
     * @param id - id empployee
     * @param active - status account
     * @return - dto
     */
    @GetMapping("/get/employee/{id}")
    public ResponseEntity<ResponseObject> getAccountEmployee(
            @Valid @PathVariable UUID id,
            @RequestParam(required = false) AccountStatus active
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.getEmployeeById(id,active))
                        .build()
        );
    }

    /**
     * get employee page
     * @param filter - data filter
     * @return - employee page
     */
    @PostMapping("/get/employees")
    public ResponseEntity<ResponseObject> getEmployees(
            @RequestBody EmployeesFilter filter
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.getAllEmployees(filter))
                        .build()
        );
    }

    /**
     * update employee
     * @param id - id employee
     * @param register - info update
     * @return - data after update
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateEmployeeById(
            @PathVariable UUID id,
            @Validated(UpdateEmployee.class) @RequestBody EmployeeRegister register
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.updateEmployee(id,register))
                        .build()
        );
    }

    /**
     * get employee by phone
     * @param phone - phone number
     * @param active - active
     * @return - employee
     */
    @GetMapping("/get/phone_number/{phone}")
    public ResponseEntity<ResponseObject> getEmployeePhoneNumber(
            @PathVariable String phone,
            @RequestParam(required = false) AccountStatus active
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.getEmployeeByPhone(phone, active))
                        .build()
        );
    }

    @PostMapping("/list/id")
    public ResponseEntity<ResponseObject> getEmployeeByIds(
            @RequestBody EmployeesFilter filter
    )
    {
        return
                ResponseEntity.ok(
                        ResponseObject.builder()
                                .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                                .data(employeeService.getEmployeeByIds(filter.getIds()))
                                .build()
                );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteEmployeeById(
            @PathVariable UUID id
    )
    {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.DELETE_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<ResponseObject> updatePassword(
            @Valid @RequestBody UpdatePassword data,
            @PathVariable UUID id
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.updatePassword(id, data))
                        .build()
        );
    }

    @GetMapping("/get/username/{username}")
    public ResponseEntity<ResponseObject> getEmployeeByUsername(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.getEmployeeByUsername(username))
                        .build()
        );
    }

    @PostMapping("/update/password/by-opt")
    public ResponseEntity<ResponseObject> updatePasswordByOPT(
            @RequestParam String password,
            @RequestParam String phone
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.updatePasswordByOPT(password, phone))
                        .build()
        );
    }

    @PostMapping("/reset")
    public ResponseEntity<ResponseObject> resetPassword(
            @RequestParam String phone
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.resetPassword(phone))
                        .build()
        );
    }

    @GetMapping("/count-shifts")
    public ResponseEntity<ResponseObject> countShiftInAppointments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(employeeService.getTopEmployees(startDate, endDate, 5))
                        .build()
        );
    }
}
