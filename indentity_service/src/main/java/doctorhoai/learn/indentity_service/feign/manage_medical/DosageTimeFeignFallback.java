package doctorhoai.learn.indentity_service.feign.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.DosageTimeDto;
import doctorhoai.learn.indentity_service.feign.dto.medical.DosageTimeFilter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DosageTimeFeignFallback implements FallbackFactory<DosageTimeFeign> {

    private final HandleFallBack fallBack;

    @Override
    public DosageTimeFeign create(Throwable cause) {
        return new DosageTimeFeign() {
            @Override
            public ResponseEntity<ResponseObject> createDosageTime(DosageTimeDto dto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getDosageTimeById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getDosageTimeByListId(DosageTimeFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateDosageTime(UUID id, DosageTimeDto dto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteDosageTime(UUID id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
