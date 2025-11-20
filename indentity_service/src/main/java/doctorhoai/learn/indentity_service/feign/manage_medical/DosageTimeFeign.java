package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.medical.DosageTimeDto;
import doctorhoai.learn.indentity_service.feign.dto.medical.DosageTimeFilter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "medical",
        contextId = "authDosageTime",
        path = "/dosage_time",
        fallbackFactory = DosageTimeFeignFallback.class,
        configuration = FeignConfig.class
)
public interface DosageTimeFeign {
    @PostMapping("/create")
    ResponseEntity<ResponseObject> createDosageTime(
            @Valid @RequestBody DosageTimeDto dto
    );

    @GetMapping("/get/{id}")
    ResponseEntity<ResponseObject> getDosageTimeById(
            @PathVariable("id") UUID id
    ) ;

    @PostMapping("/list")
    ResponseEntity<ResponseObject> getDosageTimeByListId(
            @RequestBody DosageTimeFilter filter
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateDosageTime(
            @PathVariable("id") UUID id,
            @Valid @RequestBody DosageTimeDto dto
    );

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteDosageTime(
            @PathVariable("id") UUID id
    ) ;
}
