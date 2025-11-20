package doctorhoai.learn.manage_medical.controller;

import doctorhoai.learn.manage_medical.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_medical.dto.DrugDto;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.filter.DrugFilter;
import doctorhoai.learn.manage_medical.service.drug.DrugService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("drug")
public class DrugController {

    private final DrugService drugService;

    @PostMapping("/create")
    public ResponseEntity<?> createDrug(
            @Valid @RequestBody DrugDto drugDto
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_DATA_SUCCESSFULLY.getMessage())
                        .data(drugService.createDrug(drugDto))
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getDrug(
            @PathVariable UUID id
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(drugService.getDrugById(id))
                        .build()
        );
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getDrugList(
            @RequestBody DrugFilter filter
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(drugService.getDrugPage(filter))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject>updateDrug(
            @PathVariable UUID id,
            @RequestBody DrugDto drugDto
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(drugService.updateDrug(id, drugDto))
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFULLY.getMessage())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteById(
            @PathVariable UUID id
    )
    {
        drugService.deleteByDrug(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.DELETE_DATA_SUCCESSFULLY.getMessage())
                        .build()
        );
    }
}
