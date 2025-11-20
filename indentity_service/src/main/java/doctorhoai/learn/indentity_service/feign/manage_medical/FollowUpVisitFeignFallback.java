package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.FollowUpVisitFilter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowUpVisitFeignFallback implements FallbackFactory<FollowUpVisitFeign> {

    private final HandleFallBack fallBack;

    @Override
    public FollowUpVisitFeign create(Throwable cause) {
        return new FollowUpVisitFeign() {
            @Override
            public ResponseEntity<ResponseObject> getFollowUpVisitList(FollowUpVisitFilter filter) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
