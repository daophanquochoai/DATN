package doctorhoai.learn.manage_medical.feign.feign.service;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.feign.dto.service.ServiceFilter;
import doctorhoai.learn.manage_medical.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ServiceFeignFallback implements FallbackFactory<ServiceFeign> {

    private final HandleFallBack fallBack;

    @Override
    public ServiceFeign create(Throwable cause) {
        return new ServiceFeign() {

            @Override
            public ResponseEntity<ResponseObject> getServiceById(UUID id, UUID patientId) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getServiceById(ServiceFilter filter) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
