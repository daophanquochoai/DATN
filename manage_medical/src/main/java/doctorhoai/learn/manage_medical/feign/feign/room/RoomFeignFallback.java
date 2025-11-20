package doctorhoai.learn.manage_medical.feign.feign.room;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoomFeignFallback implements FallbackFactory<RoomFeign> {

    private final HandleFallBack fallBack;

    @Override
    public RoomFeign create(Throwable cause) {
        return new RoomFeign() {
            @Override
            public ResponseEntity<ResponseObject> getRoomById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getRoomByIds(List<UUID> ids) {
                return fallBack.processFallback(cause);
            }
        };
    }
}
