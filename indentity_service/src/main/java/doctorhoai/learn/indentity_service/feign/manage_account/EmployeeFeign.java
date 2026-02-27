package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.AccountStatus;
import doctorhoai.learn.indentity_service.feign.dto.EmployeeRegister;
import doctorhoai.learn.indentity_service.feign.dto.EmployeesFilter;
import doctorhoai.learn.indentity_service.feign.dto.UpdatePassword;
import doctorhoai.learn.indentity_service.feign.dto.groupvalidate.CreateEmployee;
import doctorhoai.learn.indentity_service.feign.dto.groupvalidate.UpdateEmployee;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "authEmployee",
        path = "/employee",
        fallbackFactory = EmployeeFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface EmployeeFeign {
    /**
     * get employee by phone
     * @param phone - phone number
     * @param active - active
     * @return - employee
     */
    @GetMapping("/get/phone_number/{phone}")
    ResponseEntity<ResponseObject> getEmployeePhoneNumber(
            @PathVariable String phone,
            @RequestParam(required = false) AccountStatus active
    );

    /**
     *  register account
     * @param register - data of employee
     * @return - data after register
     */
    @PostMapping("/register-employee")
    ResponseEntity<ResponseObject> createAccountEmployee(
            @Validated(CreateEmployee.class) @RequestBody EmployeeRegister register
    );

    /**
     * get employee by id and status
     * @param id - id empployee
     * @param active - status account
     * @return - dto
     */
    @GetMapping("/get/employee/{id}")
    ResponseEntity<ResponseObject> getAccountEmployee(
            @Valid @PathVariable UUID id,
            @RequestParam(required = false) AccountStatus active
    );

    /**
     * get employee page
     * @param filter - data filter
     * @return - employee page
     */
    @PostMapping("/get/employees")
    ResponseEntity<ResponseObject> getEmployees(
            @RequestBody EmployeesFilter filter
    );

    /**
     * update employee
     * @param id - id employee
     * @param register - info update
     * @return - data after update
     */
    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateEmployeeById(
            @PathVariable UUID id,
            @Validated(UpdateEmployee.class) @RequestBody EmployeeRegister register
    );

    @PostMapping("/list/id")
    ResponseEntity<ResponseObject> getEmployeeByIds(
            @RequestBody EmployeesFilter filter
    );

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteEmployeeById(
            @PathVariable UUID id
    );

    @PutMapping("/update/password/{id}")
    ResponseEntity<ResponseObject> updatePassword(
            @Valid @RequestBody UpdatePassword data,
            @PathVariable UUID id
    );
    @GetMapping("/get/username/{username}")
    ResponseEntity<ResponseObject> getEmployeeByUsername(
            @PathVariable String username
    );

    @PostMapping("/update/password/by-opt")
    ResponseEntity<ResponseObject> updatePasswordByOPT(
            @RequestParam String password,
            @RequestParam String phone
    );

    @PostMapping("/reset")
    ResponseEntity<ResponseObject> resetPassword(
            @RequestParam String phone
    );

    @GetMapping("/count-shifts")
    ResponseEntity<ResponseObject> countShiftInAppointments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );
}
