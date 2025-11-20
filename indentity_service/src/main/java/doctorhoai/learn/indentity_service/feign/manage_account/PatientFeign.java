package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.AccountStatus;
import doctorhoai.learn.indentity_service.feign.dto.PatientsFilter;
import doctorhoai.learn.indentity_service.feign.dto.PatientsRegister;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "authPatient",
        path = "/patient",
        fallbackFactory = PatientFeignFallBack.class,
        configuration = FeignConfig.class
)
public interface PatientFeign {
    /**
     * get patient by phone
     * @param phone - phone number
     * @param status - status
     * @return - patient dto
     */
    @GetMapping("/get/phone_number/{phone}")
    ResponseEntity<ResponseObject> getPatientByPhoneNumber(
            @PathVariable String phone,
            @RequestParam(required = false) AccountStatus status
    );
    /**
     * create account of user
     * @param register - data for register
     * @return - data user
     */
    @PostMapping("/register-user")
    ResponseEntity<ResponseObject> createAccountPatient(
            @Valid @RequestBody PatientsRegister register
    );

    /**
     * get patient by id
     * @param id - id patient
     * @param status - status account
     * @return - patient dto
     */
    @GetMapping("/get/patient/{id}")
    ResponseEntity<?> getPatientsById(
            @PathVariable UUID id,
            @RequestParam(required = false) AccountStatus status
    );

    /**
     * get patient list
     * @param filter - filter data
     * @return - patient list
     */
    @PostMapping("/get/patients")
    ResponseEntity<ResponseObject> getPatientsList(
            @RequestBody PatientsFilter filter
    );

    /**
     * update patient
     * @param id - id patient
     * @param register - register info
     * @return - data patient
     */
    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updatePatients(
            @PathVariable UUID id,
            @RequestBody PatientsRegister register
    );

    @PostMapping("/list/id")
    ResponseEntity<ResponseObject> getPatientByIds(
            @RequestBody PatientsFilter filter
    );

    @GetMapping("/check/phone/{phone}")
    ResponseEntity<Boolean> checkNumberPhone(
            @PathVariable String phone
    );

    @PutMapping("/update/password/{id}")
    ResponseEntity<ResponseObject> updatePassword(
            @PathVariable UUID id,
            @Valid @RequestBody PatientsRegister register
    );

    @GetMapping("/get/username/{username}")
    ResponseEntity<ResponseObject> getPatientByUsername(
            @PathVariable String username
    );

    @PostMapping("/update/forget")
    ResponseEntity<ResponseObject> updatePasswordByOpt(
            @RequestParam String phone,
            @RequestParam String password
    );
}
