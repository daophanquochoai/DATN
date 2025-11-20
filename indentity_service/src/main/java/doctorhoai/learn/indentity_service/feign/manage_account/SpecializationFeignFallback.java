package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.SpecializationFilter;
import doctorhoai.learn.indentity_service.feign.dto.SpecializationsDto;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SpecializationFeignFallback implements FallbackFactory<SpecializationFeign> {

    private final HandleFallBack fallBack;

    @Override
    public SpecializationFeign create(Throwable cause) {
        return new SpecializationFeign() {
            @Override
            public ResponseEntity<?> createSpecialization(SpecializationsDto dto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getSpecializationById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getSpecializationList(SpecializationFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateSpecializationById(UUID id, SpecializationsDto dto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteSpecializationById(UUID id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
