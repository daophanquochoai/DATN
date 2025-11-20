package doctorhoai.learn.manage_medical.controller;

import doctorhoai.learn.manage_medical.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_medical.dto.DosageTimeDto;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.filter.DosageTimeFilter;
import doctorhoai.learn.manage_medical.service.dosage_time.DosageTimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("dosage_time")
@RequiredArgsConstructor
public class DosageTimeController {
    private final DosageTimeService dosageTimeService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createDosageTime(
            @Valid @RequestBody DosageTimeDto dto
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(dosageTimeService.createDosageTime(dto))
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getDosageTimeById(
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(dosageTimeService.getDosageTimeById(id))
                        .build()
        );
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getDosageTimeByListId(
            @RequestBody DosageTimeFilter filter
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(dosageTimeService.getDosageTimes(filter))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateDosageTime(
            @PathVariable("id") UUID id,
            @Valid @RequestBody DosageTimeDto dto
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(dosageTimeService.updateDosageTime(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteDosageTime(
            @PathVariable("id") UUID id
            ) {
        dosageTimeService.deleteDosageTime(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.DELETE_DATA_SUCCESSFULLY.getMessage())
                        .build()
        );
    }
}
