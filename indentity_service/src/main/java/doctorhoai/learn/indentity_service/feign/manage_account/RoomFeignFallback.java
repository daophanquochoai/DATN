package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.RoomDto;
import doctorhoai.learn.indentity_service.feign.dto.RoomFilter;
import doctorhoai.learn.indentity_service.feign.function.HandleFallBack;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoomFeignFallback implements FallbackFactory<RoomFeign> {

    private final HandleFallBack fallBack;

    @Override
    public RoomFeign create(Throwable cause) {
        return new RoomFeign() {
            @Override
            public ResponseEntity<ResponseObject> createRoom(RoomDto roomDto) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getRoomById(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getRoomList(RoomFilter filter) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> updateRoom(RoomDto roomDto, UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> deleteRoom(UUID id) {
                return fallBack.processFallback(cause);
            }

            @Override
            public ResponseEntity<ResponseObject> getTotalRoom() {
                return fallBack.processFallback(cause);
            }
        };
    }
}
