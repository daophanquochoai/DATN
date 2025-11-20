package doctorhoai.learn.manage_medical.feign.feign.room;

import doctorhoai.learn.manage_medical.dto.ResponseObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "manageuser",
        contextId = "medicalRoom",
        path = "/room",
        fallbackFactory = RoomFeignFallback.class
)
public interface RoomFeign {
    @GetMapping("/get/{id}")
    ResponseEntity<ResponseObject> getRoomById(
            @PathVariable UUID id
    );

    @GetMapping("/get/ids")
    ResponseEntity<ResponseObject> getRoomByIds(
            @RequestParam(required = false) List<UUID> ids
    );
}
