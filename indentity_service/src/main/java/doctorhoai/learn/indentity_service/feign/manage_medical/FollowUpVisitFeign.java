package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.medical.FollowUpVisitFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "medical",
        contextId = "authFollowUpVisitFeign",
        path = "/follow_up_visit",
        fallbackFactory = DrugFeignFallback.class,
        configuration = FeignConfig.class
)
public interface FollowUpVisitFeign {
    @PostMapping("/list")
    ResponseEntity<ResponseObject> getFollowUpVisitList(
            @RequestBody FollowUpVisitFilter filter
    );
}
