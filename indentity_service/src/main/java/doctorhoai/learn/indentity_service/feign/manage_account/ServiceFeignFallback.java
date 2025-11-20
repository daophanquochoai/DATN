package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.ServiceDto;
import doctorhoai.learn.indentity_service.feign.dto.ServiceFilter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
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
            public ResponseEntity<ResponseObject> createService(ServiceDto serviceDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getServiceById(UUID id, UUID patientId) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getAllService(ServiceFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateService(UUID id, ServiceDto serviceDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteService(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getServiceById(ServiceFilter filter) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
