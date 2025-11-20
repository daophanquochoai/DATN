package doctorhoai.learn.indentity_service.business.manage_account;

import doctorhoai.learn.indentity_service.business.sms.SmsController;
import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.AccountStatus;
import doctorhoai.learn.indentity_service.feign.dto.PatientsFilter;
import doctorhoai.learn.indentity_service.feign.dto.PatientsRegister;
import doctorhoai.learn.indentity_service.feign.manage_account.PatientFeign;
import doctorhoai.learn.indentity_service.util.FunctionCommon;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientFeign patientFeign;
    private final RedisTemplate<String, String> redisTemplate;
    private final FunctionCommon functionCommon;

    /**
     * get patient by phone
     * @param phone - phone number
     * @param status - status
     * @return - patient dto
     */
    @GetMapping("/get/phone_number/{phone}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    ResponseEntity<ResponseObject> getPatientByPhoneNumber(
            @PathVariable String phone,
            @RequestParam(required = false) AccountStatus status
    ){
        return patientFeign.getPatientByPhoneNumber(phone,status);
    }
    /**
     * create account of user
     * @param register - data for register
     * @return - data user
     */
    @PostMapping("/register-user/{code}")
    ResponseEntity<ResponseObject> createAccountPatient(
            @Valid @RequestBody PatientsRegister register,
            @PathVariable String code
    ){
        String optSaved = redisTemplate.opsForValue().get(register.getPhoneNumber());
        if( optSaved.equals(code)){
            return patientFeign.createAccountPatient(register);
        }else{
            return ResponseEntity.status(400).body(
                    ResponseObject.builder()
                            .message("Can't verify")
                            .build()
            );
        }
    }

    /**
     * get patient by id
     * @param id - id patient
     * @param status - status account
     * @return - patient dto
     */
    @GetMapping("/get/patient/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    ResponseEntity<?> getPatientsById(
            @PathVariable UUID id,
            @RequestParam(required = false) AccountStatus status
    )
    {
        return patientFeign.getPatientsById(id, status);
    }

    /**
     * get patient list
     * @param filter - filter data
     * @return - patient list
     */
    @PostMapping("/get/patients")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    ResponseEntity<ResponseObject> getPatientsList(
            @RequestBody PatientsFilter filter
    )
    {
        return patientFeign.getPatientsList(filter);
    }

    /**
     * update patient
     * @param id - id patient
     * @param register - register info
     * @return - data patient
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    ResponseEntity<ResponseObject> updatePatients(
            @PathVariable UUID id,
            @RequestBody PatientsRegister register
    )
    {
        return  patientFeign.updatePatients(id,register);
    }

    @PostMapping("/list/id")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    ResponseEntity<ResponseObject> getPatientByIds(
            @RequestBody PatientsFilter filter
    )
    {
        return patientFeign.getPatientByIds(filter);
    }

    @PutMapping("/update/password/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public ResponseEntity<ResponseObject> updatePassword(
            @PathVariable UUID id,
            @Valid @RequestBody PatientsRegister register
    ){
        return patientFeign.updatePassword(id,register);
    }

    @GetMapping("/get/username/{username}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<ResponseObject> getEmployeeByUsername(
            @PathVariable String username
    ) {
        return patientFeign.getPatientByUsername(username);
    }

    @GetMapping("/forgot/{phoneNumber}")
    public ResponseEntity<ResponseObject> forgotPassword(
            @PathVariable String phoneNumber
    ){
        String optSaved = redisTemplate.opsForValue().get(phoneNumber);
        if( optSaved != null){
            redisTemplate.delete(phoneNumber);
        }
        String code = functionCommon.randomOpt(6);

        boolean check = functionCommon.sendSmsWithContent("Mã xác thực của bạn là " + code, phoneNumber);
        if( !check){
            return ResponseEntity.status(400).body(
                    ResponseObject.builder()
                            .message("Hệ thống đang bận")
                            .build()
            );
        }
        System.out.println("Verification code: " + code);
        // save to redis
        redisTemplate.opsForValue().set(
                phoneNumber,
                code,
                Duration.ofMinutes(2)
        );

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Verification code sent to phone number")
                        .build()
        );
    }

    @GetMapping("/verify/{phoneNumber}/{code}")
    public ResponseEntity<ResponseObject> verifyCode(
            @PathVariable String phoneNumber,
            @PathVariable String code
    ) {
        String optSaved = redisTemplate.opsForValue().get(phoneNumber);
        if (optSaved != null && optSaved.equals(code)) {
            redisTemplate.delete(phoneNumber);
            String opt = functionCommon.randomOpt(8);
            System.out.println("OPT : " + opt);
            redisTemplate.opsForValue().set(phoneNumber, opt, Duration.ofMinutes(20));

            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("Verification successful")
                            .data(opt)
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
    @GetMapping("/update/forget")
    ResponseEntity<ResponseObject> updatePasswordByOPT(
            @RequestParam String opt,
            @RequestParam String phone,
            @RequestParam String password
    )
    {
        String optSaved = redisTemplate.opsForValue().get(phone);
        if( optSaved != null && optSaved.equals(opt)){
            redisTemplate.delete(phone);
            return patientFeign.updatePasswordByOpt(phone, password);
        }
        else {
            return ResponseEntity.status(400).body(
                    ResponseObject.builder()
                            .message("Can't verify")
                            .build()
            );
        }
    }
}

