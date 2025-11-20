package doctorhoai.learn.indentity_service.business.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.DosageTimeDto;
import doctorhoai.learn.indentity_service.feign.dto.medical.DosageTimeFilter;
import doctorhoai.learn.indentity_service.feign.manage_medical.DosageTimeFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("dosage_time")
public class DosageTimeController {

    private final DosageTimeFeign dosageTimeFeign;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> createDosageTime(
            @Valid @RequestBody DosageTimeDto dto
    )
    {
        return dosageTimeFeign.createDosageTime(dto);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    ResponseEntity<ResponseObject> getDosageTimeById(
            @PathVariable("id") UUID id
    )
    {
        return dosageTimeFeign.getDosageTimeById(id);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    ResponseEntity<ResponseObject> getDosageTimeByListId(
            @RequestBody DosageTimeFilter filter
    )
    {
        return dosageTimeFeign.getDosageTimeByListId(filter);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    ResponseEntity<ResponseObject> updateDosageTime(
            @PathVariable("id") UUID id,
            @Valid @RequestBody DosageTimeDto dto
    )
    {
        return dosageTimeFeign.updateDosageTime(id,dto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    ResponseEntity<ResponseObject> deleteDosageTime(
            @PathVariable("id") UUID id
    )
    {
        return dosageTimeFeign.deleteDosageTime(id);
    }
}
