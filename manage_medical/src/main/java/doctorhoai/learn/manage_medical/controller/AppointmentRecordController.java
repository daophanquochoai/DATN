package doctorhoai.learn.manage_medical.controller;

import doctorhoai.learn.manage_medical.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.request.AppointmentRecordCreate;
import doctorhoai.learn.manage_medical.service.appointment_records.AppointRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("appointment_record")
@RequiredArgsConstructor
public class AppointmentRecordController {

    private final AppointRecordService appointRecordService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createAppointmentRecord(
            @Valid @RequestBody AppointmentRecordCreate dto
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_DATA_SUCCESSFULLY.getMessage())
                        .data(appointRecordService.createAppointmentRecord(dto))
                        .build()
        );
    }

    @GetMapping("/get/appointment/{id}")
    public ResponseEntity<ResponseObject> getAppointmentRecordById(
            @PathVariable UUID id
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(appointRecordService.getAppointmentById(id))
                        .build()
        );
    }
}
