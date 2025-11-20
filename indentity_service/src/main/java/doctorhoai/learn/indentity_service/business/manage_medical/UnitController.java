package doctorhoai.learn.indentity_service.business.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.UnitFilter;
import doctorhoai.learn.indentity_service.feign.dto.medical.UnitsDto;
import doctorhoai.learn.indentity_service.feign.manage_medical.UnitFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("unit")
public class UnitController {

    private final UnitFeign unitFeign;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> createUnit(
            @Valid @RequestBody UnitsDto unitsDto
    )
    {
        return unitFeign.createUnit(unitsDto);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getUnitById(
            @PathVariable("id") UUID id
    )
    {
        return unitFeign.getUnitById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> updateUnit(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UnitsDto unitsDto
    )
    {
        return unitFeign.updateUnit(id,unitsDto);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getListUnit(
            @RequestBody UnitFilter filter
    )
    {
        return unitFeign.getListUnit(filter);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> deleteUnit(
            @PathVariable UUID id
    )
    {
        return unitFeign.deleteUnit(id);
    }
}
