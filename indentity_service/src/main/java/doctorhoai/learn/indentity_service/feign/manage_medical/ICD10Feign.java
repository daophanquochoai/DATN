package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.medical.ICD10Filter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "medical",
        contextId = "authicd10",
        path = "/icd10",
        fallbackFactory = DrugFeignFallback.class,
        configuration = FeignConfig.class
)
public interface ICD10Feign {

    @PostMapping("/list")
    ResponseEntity<ResponseObject> getICD10(
            @RequestBody ICD10Filter filter
    );
}
