package doctorhoai.learn.manage_medical.feign.feign.service;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.feign.config.FeignConfig;
import doctorhoai.learn.manage_medical.feign.dto.service.ServiceFilter;
import doctorhoai.learn.manage_medical.feign.feign.patient.PatientFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "medicalService",
        path = "/service",
        fallbackFactory = ServiceFeignFallback.class,
        configuration = FeignConfig.class
)
public interface ServiceFeign {
    @GetMapping("/get/{id}")
    ResponseEntity<ResponseObject> getServiceById(
            @PathVariable("id") UUID id,
            @RequestParam(required = false) UUID patientId
    );
    @PostMapping("/list/id")
    ResponseEntity<ResponseObject> getServiceById(
            @RequestBody ServiceFilter filter
    );


}
