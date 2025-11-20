package doctorhoai.learn.indentity_service.feign.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.config.FeignConfig;
import doctorhoai.learn.indentity_service.feign.dto.RoomDto;
import doctorhoai.learn.indentity_service.feign.dto.RoomFilter;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "authRoom",
        path = "/room",
        fallbackFactory = RoomFeignFallback.class,
        configuration = FeignConfig.class
)
public interface RoomFeign {
    @PostMapping("/create")
     ResponseEntity<ResponseObject> createRoom(
            @Valid @RequestBody RoomDto roomDto
    );

    @GetMapping("/get/{id}")
    ResponseEntity<ResponseObject> getRoomById(
            @PathVariable UUID id
    );

    @PostMapping("/list")
    ResponseEntity<ResponseObject> getRoomList(
            @RequestBody RoomFilter filter
    );

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateRoom(
            @RequestBody RoomDto roomDto,
            @PathVariable UUID id
    );

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteRoom(
            @PathVariable UUID id
    );

    @GetMapping("/total" )
    ResponseEntity<ResponseObject> getTotalRoom();
}
