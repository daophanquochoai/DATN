package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.medical.DrugDto;
import doctorhoai.learn.indentity_service.feign.dto.medical.DrugFilter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "medical",
        contextId = "authdrug",
        path = "/drug",
        fallbackFactory = DrugFeignFallback.class,
        configuration = FeignConfig.class
)
public interface DrugFeign {
    @PostMapping("/create")
    ResponseEntity<?> createDrug(
            @Valid @RequestBody DrugDto drugDto
    );

    @GetMapping("/get/{id}")
    ResponseEntity<ResponseObject> getDrug(
            @PathVariable UUID id
    );

    @PostMapping("/list")
    ResponseEntity<ResponseObject> getDrugList(
            @RequestBody DrugFilter filter
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject>updateDrug(
            @PathVariable UUID id,
            @RequestBody DrugDto drugDto
    );
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteById(
            @PathVariable UUID id
    );
}
