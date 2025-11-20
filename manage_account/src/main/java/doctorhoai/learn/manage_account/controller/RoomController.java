package doctorhoai.learn.manage_account.controller;

import doctorhoai.learn.manage_account.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_account.dto.ResponseObject;
import doctorhoai.learn.manage_account.dto.RoomDto;
import doctorhoai.learn.manage_account.dto.filter.RoomFilter;
import doctorhoai.learn.manage_account.service.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createRoom(
            @Valid @RequestBody RoomDto roomDto
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(roomService.createRoom(roomDto))
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseObject> getRoomById(
            @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(roomService.getRoomById(id))
                        .build()
        );
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getRoomList(
            @RequestBody RoomFilter filter
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .data(roomService.getRoomsList(filter))
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateRoom(
            @RequestBody RoomDto roomDto,
            @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.UPDATE_DATA_SUCCESSFUL.getMessage())
                        .data(roomService.updateRoom(id, roomDto))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteRoom(
            @PathVariable UUID id
    )
    {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.DELETE_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @GetMapping("/get/ids")
    public ResponseEntity<ResponseObject> getRoomByIds(
            @RequestParam(required = false) List<UUID> ids
    )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(roomService.getRoomByIds(ids))
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }

    @GetMapping("/total" )
    public ResponseEntity<ResponseObject> getTotalRoom() {
        Map<String, Long> returnValue = Map.of(
                "totalRooms",roomService.getTotalRoom()
        );
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(returnValue)
                        .message(EMessageResponse.GET_DATA_SUCCESSFUL.getMessage())
                        .build()
        );
    }
}
