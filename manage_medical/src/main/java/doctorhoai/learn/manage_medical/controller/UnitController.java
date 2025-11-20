package doctorhoai.learn.manage_medical.controller;

import doctorhoai.learn.manage_medical.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.UnitsDto;
import doctorhoai.learn.manage_medical.dto.filter.UnitFilter;
import doctorhoai.learn.manage_medical.service.units.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createUnit(
            @Valid @RequestBody UnitsDto unitsDto
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.CREATE_DATA_SUCCESSFULLY.getMessage())
                        .data(unitService.createUnit(unitsDto))
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getUnitById(
            @PathVariable("id") UUID id
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(unitService.getUnitById(id))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUnit(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UnitsDto unitsDto
    ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFULLY.getMessage())
                        .data(unitService.updateUnit(id, unitsDto))
                        .build()
        );
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getListUnit(
            @RequestBody UnitFilter filter
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(unitService.getListUnit(filter))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteUnit(
            @PathVariable UUID id
    )
    {
        unitService.deleteUnit(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.DELETE_DATA_SUCCESSFULLY.getMessage())
                        .build()
        );
    }
}
