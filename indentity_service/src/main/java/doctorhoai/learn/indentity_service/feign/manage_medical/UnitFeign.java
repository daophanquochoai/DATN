package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.medical.UnitFilter;
import doctorhoai.learn.indentity_service.feign.dto.medical.UnitsDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "medical",
        contextId = "authUnit",
        path = "/unit",
        fallbackFactory = AppointmentRecordFeignFallback.class,
        configuration = FeignConfig.class
)
public interface UnitFeign {
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createUnit(
            @Valid @RequestBody UnitsDto unitsDto
    );

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getUnitById(
            @PathVariable("id") UUID id
    );

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUnit(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UnitsDto unitsDto
    );

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getListUnit(
            @RequestBody UnitFilter filter
    );

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteUnit(
            @PathVariable UUID id
    );
}
