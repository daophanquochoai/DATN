package doctorhoai.learn.manage_medical.feign.feign.shift;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShiftFeignFallback implements FallbackFactory<ShiftFeign> {

    private final HandleFallBack fallBack;

    @Override
    public ShiftFeign create(Throwable cause) {
        return new ShiftFeign() {
            @Override
            public ResponseEntity<ResponseObject> getShiftEmployeeById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getShiftEmployeeByIds(List<UUID> ids) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getShiftEmployeeByEmployeeIds(List<UUID> ids, LocalDate date) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getShiftEmployeeByEmployeeId(UUID id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
