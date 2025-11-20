package doctorhoai.learn.indentity_service.business.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.DrugDto;
import doctorhoai.learn.indentity_service.feign.dto.medical.DrugFilter;
import doctorhoai.learn.indentity_service.feign.manage_medical.DrugFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("drug")
@RequiredArgsConstructor
public class DrugController {

    private final DrugFeign drugFeign;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<?> createDrug(
            @Valid @RequestBody DrugDto drugDto
    )
    {
        return drugFeign.createDrug(drugDto);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getDrug(
            @PathVariable UUID id
    )
    {
        return drugFeign.getDrug(id);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getDrugList(
            @RequestBody DrugFilter filter
    )
    {
        return drugFeign.getDrugList(filter);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> updateDrug(
            @PathVariable UUID id,
            @RequestBody DrugDto drugDto
    )
    {
        return drugFeign.updateDrug(id,drugDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> deleteById(
            @PathVariable UUID id
    )
    {
        return drugFeign.deleteById(id);
    }

}
