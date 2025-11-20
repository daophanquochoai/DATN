package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.ServiceDto;
import doctorhoai.learn.indentity_service.feign.dto.ServiceFilter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@FeignClient(
        name = "manageuser",
        contextId = "authService",
        path = "/service",
        fallbackFactory = ServiceFeignFallback.class,
        configuration = FeignConfig.class
)
public interface ServiceFeign {
    @PostMapping("/create")
    ResponseEntity<ResponseObject> createService(
            @Valid @RequestBody ServiceDto serviceDto
    );

    @GetMapping("/get/{id}")
    ResponseEntity<ResponseObject> getServiceById(
            @PathVariable("id") UUID id,
            @RequestParam(required = false) UUID patientId
    );

    @PostMapping("/list")
    ResponseEntity<ResponseObject> getAllService(
            @RequestBody ServiceFilter filter
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateService(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ServiceDto serviceDto
    );

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteService(
            @PathVariable("id") UUID id
    );

    @PostMapping("/list/id")
    ResponseEntity<ResponseObject> getServiceById(
            @RequestBody ServiceFilter filter
    );
}
