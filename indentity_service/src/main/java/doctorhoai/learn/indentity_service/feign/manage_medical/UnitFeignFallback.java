package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.UnitFilter;
import doctorhoai.learn.indentity_service.feign.dto.medical.UnitsDto;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UnitFeignFallback implements FallbackFactory<UnitFeign> {

    private final HandleFallBack fallBack;

    @Override
    public UnitFeign create(Throwable cause) {
        return new UnitFeign() {
            @Override
            public ResponseEntity<ResponseObject> createUnit(UnitsDto unitsDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getUnitById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateUnit(UUID id, UnitsDto unitsDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getListUnit(UnitFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteUnit(UUID id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
