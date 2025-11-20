package doctorhoai.learn.indentity_service.business.manage_account;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.RoomDto;
import doctorhoai.learn.indentity_service.feign.dto.RoomFilter;
import doctorhoai.learn.indentity_service.feign.manage_account.RoomFeign;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomFeign roomFeign;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> createRoom(
            @Valid @RequestBody RoomDto roomDto
    ){
        return roomFeign.createRoom(roomDto);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    ResponseEntity<ResponseObject> getRoomById(
            @PathVariable UUID id
    ){
        return roomFeign.getRoomById(id);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    ResponseEntity<ResponseObject> getRoomList(
            @RequestBody RoomFilter filter
    ){
        return roomFeign.getRoomList(filter);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> updateRoom(
            @RequestBody RoomDto roomDto,
            @PathVariable UUID id
    ){
        return roomFeign.updateRoom(roomDto,id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> deleteRoom(
            @PathVariable UUID id
    ){
        return roomFeign.deleteRoom(id);
    }
    @GetMapping("/total" )
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<ResponseObject> getTotalRoom(){
        return roomFeign.getTotalRoom();
    }
}
