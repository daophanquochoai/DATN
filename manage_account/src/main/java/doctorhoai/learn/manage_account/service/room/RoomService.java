package doctorhoai.learn.manage_account.service.room;

import doctorhoai.learn.manage_account.dto.RoomDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.RoomFilter;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    RoomDto createRoom(RoomDto roomDto);
    RoomDto updateRoom(UUID id, RoomDto roomDto);
    RoomDto getRoomById(UUID id);
    PageObject getRoomsList(RoomFilter filter);
    void deleteRoom(UUID id);
    List<RoomDto> getRoomByIds(List<UUID> ids);
    Long getTotalRoom();
}
