package doctorhoai.learn.indentity_service.business.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.AccountStatus;
import doctorhoai.learn.indentity_service.feign.dto.EmployeeRegister;
import doctorhoai.learn.indentity_service.feign.dto.EmployeesFilter;
import doctorhoai.learn.indentity_service.feign.dto.UpdatePassword;
import doctorhoai.learn.indentity_service.feign.dto.groupvalidate.CreateEmployee;
import doctorhoai.learn.indentity_service.feign.dto.groupvalidate.UpdateEmployee;
import doctorhoai.learn.indentity_service.feign.manage_account.EmployeeFeign;
import doctorhoai.learn.indentity_service.util.FunctionCommon;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeFeign employeeFeign;
    private final FunctionCommon functionCommon;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * get employee by phone
     * @param phone - phone number
     * @param active - active
     * @return - employee
     */
    @GetMapping("/get/phone_number/{phone}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    ResponseEntity<ResponseObject> getEmployeePhoneNumber(
            @PathVariable String phone,
            @RequestParam(required = false) AccountStatus active
    ){
        return employeeFeign.getEmployeePhoneNumber(phone,active);
    }

    @GetMapping("/get/username/{username}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<ResponseObject> getEmployeeByUsername(
            @PathVariable String username
    )
    {
        return employeeFeign.getEmployeeByUsername(username);
    }

    /**
     *  register account
     * @param register - data of employee
     * @return - data after register
     */
    @PostMapping("/register-employee")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> createAccountEmployee(
            @Validated(CreateEmployee.class) @RequestBody EmployeeRegister register
    ){
        return employeeFeign.createAccountEmployee(register);
    }

    /**
     * get employee by id and status
     * @param id - id empployee
     * @param active - status account
     * @return - dto
     */
    @GetMapping("/get/employee/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    ResponseEntity<ResponseObject> getAccountEmployee(
            @Valid @PathVariable UUID id,
            @RequestParam(required = false) AccountStatus active
    ){
        return employeeFeign.getAccountEmployee(id,active);
    }

    /**
     * get employee page
     * @param filter - data filter
     * @return - employee page
     */
    @PostMapping("/get/employees")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    ResponseEntity<ResponseObject> getEmployees(
            @RequestBody EmployeesFilter filter
    ){
        return employeeFeign.getEmployees(filter);
    }

    /**
     * update employee
     * @param id - id employee
     * @param register - info update
     * @return - data after update
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    ResponseEntity<ResponseObject> updateEmployeeById(
            @PathVariable UUID id,
            @Validated(UpdateEmployee.class) @RequestBody EmployeeRegister register
    ){
        return employeeFeign.updateEmployeeById(id, register);
    }

    @PostMapping("/list/id")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    ResponseEntity<ResponseObject> getEmployeeByIds(
            @RequestBody EmployeesFilter filter
    ){
        return employeeFeign.getEmployeeByIds(filter);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    ResponseEntity<ResponseObject> deleteEmployeeById(
            @PathVariable UUID id
    ){
        return employeeFeign.deleteEmployeeById(id);
    }

    @PutMapping("/update/password/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    ResponseEntity<ResponseObject> updatePassword(
            @Valid @RequestBody UpdatePassword data,
            @PathVariable UUID id
    ){
        return employeeFeign.updatePassword(data,id);
    }

    @GetMapping("/forget/{phone}")
    ResponseEntity<ResponseObject> forgetPassword(
            @PathVariable String phone
    ){
        String optSaved = redisTemplate.opsForValue().get(phone);
        if( optSaved != null){
            redisTemplate.delete(phone);
        }

        String opt = functionCommon.randomOpt(6);
        boolean check = functionCommon.sendSmsWithContent("Mã xác thực của bạn là " + opt, phone);
        if( !check){
            return ResponseEntity.status(400).body(
                    ResponseObject.builder()
                            .message("Hệ thống đang bận")
                            .build()
            );
        }
        System.out.println("OPT : " + opt);
        redisTemplate.opsForValue().set(phone, opt , java.time.Duration.ofMinutes(2));
        return employeeFeign.getEmployeePhoneNumber(phone, AccountStatus.ACTIVE);
    }

    @GetMapping("/verify/{opt}/{phone}")
    ResponseEntity<ResponseObject> verifyForgetPassword(
            @PathVariable String opt,
            @PathVariable String phone
    ) {
        String optSaved = redisTemplate.opsForValue().get(phone);
        if (optSaved != null && optSaved.equals(opt)) {
            redisTemplate.delete(phone);
            String code = functionCommon.randomOpt(8);
            System.out.println("OPT : " + code);
            redisTemplate.opsForValue().set(phone, code, java.time.Duration.ofMinutes(20));
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .data(code)
                            .build()
            );
        } else {
            return ResponseEntity.status(400).body(
                    ResponseObject.builder()
                            .message("Can't verify")
                            .build()
            );
        }
    }

    @GetMapping("/update/forget/password")
    ResponseEntity<ResponseObject> updatePasswordByOPT(
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String code
    ){
        String codeSaved = redisTemplate.opsForValue().get(phone);
        if( codeSaved != null && codeSaved.equals(code)){
            redisTemplate.delete(phone);
            return employeeFeign.updatePasswordByOPT(password, phone);
        }else{
            return ResponseEntity.status(400).body(
                    ResponseObject.builder()
                            .message("Can't verify")
                            .build()
            );
        }
    }

    @GetMapping("/reset")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<ResponseObject> resetPassword(
            @RequestParam String phone
    )
    {
        return employeeFeign.resetPassword(phone);
    }

}
