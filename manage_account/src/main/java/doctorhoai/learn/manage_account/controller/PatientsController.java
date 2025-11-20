package doctorhoai.learn.manage_account.controller;

import doctorhoai.learn.manage_account.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_account.dto.PatientsRegister;
import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.filter.PatientsFilter;
import doctorhoai.learn.manage_account.model.Account.AccountStatus;
import doctorhoai.learn.manage_account.service.patients.PatientsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("patient")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PatientsController {
    private final PatientsService patientsService;

    /**
     * create account of user
     * @param register - data for register
     * @return - data user
     */
    @PostMapping("/register-user")
    public ResponseEntity<ResponseObject> createAccountPatient(
                @Valid @RequestBody PatientsRegister register
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.SAVE_DATA_SUCCESSFUL.getMessage())
                        .data(patientsService.registerPatient(register))
                        .build()
        );
    }

    /**
     * get patient by id
     * @param id - id patient
     * @param status - status account
     * @return - patient dto
     */
    @GetMapping("/get/patient/{id}")
    public ResponseEntity<?> getPatientsById(
            @PathVariable UUID id,
            @RequestParam(required = false) AccountStatus status
            ){
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                            .data(patientsService.getPatientsById(id, status))
                            .build()
            );
    }

    /**
     * get patient list
     * @param filter - filter data
     * @return - patient list
     */
    @PostMapping("/get/patients")
    public ResponseEntity<ResponseObject> getPatientsList(
            @RequestBody PatientsFilter filter
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(patientsService.getPatientsPage(filter))
                        .build()
        );
    }

    /**
     * update patient
     * @param id - id patient
     * @param register - register info
     * @return - data patient
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updatePatients(
            @PathVariable UUID id,
            @RequestBody PatientsRegister register
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(patientsService.updatePatient(id, register))
                        .build()
        );
    }

    /**
     * get patient by phone
     * @param phone - phone number
     * @param status - status
     * @return - patient dto
     */
    @GetMapping("/get/phone_number/{phone}")
    public ResponseEntity<ResponseObject> getPatientByPhoneNumber(
            @PathVariable String phone,
            @RequestParam(required = false) AccountStatus status
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(patientsService.getPatientsByPhone(phone, status))
                        .build()
        );
    }

    @PostMapping("/list/id")
    public ResponseEntity<ResponseObject> getPatientByIds(
            @RequestBody PatientsFilter filter
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(patientsService.getPatientsByIds(filter.getIds()))
                        .build()
        );
    }

    @GetMapping("/check/phone/{phone}")
    public ResponseEntity<Boolean> checkNumberPhone(
            @PathVariable String phone
    )
    {
        return ResponseEntity.ok(
                patientsService.checkNumberPhone(phone)
        );
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<ResponseObject> updatePassword(
            @PathVariable UUID id,
            @Valid @RequestBody PatientsRegister register
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(patientsService.updatePatient(id,register))
                        .build()
        );
    }

    @GetMapping("/get/username/{username}")
    public ResponseEntity<ResponseObject> getPatientByUsername(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(patientsService.getPatientByUsername(username))
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllPatients(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<UUID> ids
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(patientsService.getAllPatients(ids, search))
                        .build()
        );
    }

    @PostMapping("/update/forget")
    public ResponseEntity<ResponseObject> updatePasswordByOpt(
            @RequestParam String phone,
            @RequestParam String password
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(patientsService.updatePasswordByOpt(phone, password))
                        .build()
        );
    }
}
