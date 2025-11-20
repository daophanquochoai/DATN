package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.ICD10Filter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ICD10FeignFallback implements FallbackFactory<ICD10Feign> {

    private final HandleFallBack fallBack;

    @Override
    public ICD10Feign create(Throwable cause) {
        return new ICD10Feign() {
            @Override
            public ResponseEntity<ResponseObject> getICD10(ICD10Filter filter) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
