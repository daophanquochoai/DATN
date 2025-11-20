package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.DrugDto;
import doctorhoai.learn.indentity_service.feign.dto.medical.DrugFilter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DrugFeignFallback implements FallbackFactory<DrugFeign> {

    private final HandleFallBack fallBack;

    @Override
    public DrugFeign create(Throwable cause) {
        return new DrugFeign() {
            @Override
            public ResponseEntity<?> createDrug(DrugDto drugDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getDrug(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getDrugList(DrugFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateDrug(UUID id, DrugDto drugDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteById(UUID id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
