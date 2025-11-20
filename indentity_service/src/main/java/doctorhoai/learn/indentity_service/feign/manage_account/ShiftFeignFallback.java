package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.ShiftEmployeeCreate;
import doctorhoai.learn.indentity_service.feign.dto.ShiftEmployeeFilter;
import doctorhoai.learn.indentity_service.feign.dto.ShiftFilter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
            public ResponseEntity<ResponseObject> getShiftList(ShiftFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> createShiftEmployee(ShiftEmployeeCreate create) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getShiftEmployee(ShiftEmployeeFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getShiftEmployeeById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getShiftEmployeeByIds(List<UUID> ids) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getShiftEmployeeByEmployeeIds(List<UUID> ids) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteShift(UUID id) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
