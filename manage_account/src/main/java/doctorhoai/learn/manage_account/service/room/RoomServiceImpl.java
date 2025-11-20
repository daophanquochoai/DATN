package doctorhoai.learn.manage_account.service.room;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_account.dto.EmployeeDto;
import doctorhoai.learn.manage_account.dto.RoomDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.RoomFilter;
import doctorhoai.learn.manage_account.exception.exception.RoomNotFoundException;
import doctorhoai.learn.manage_account.mapper.Mapper;
import doctorhoai.learn.manage_account.model.Employees;
import doctorhoai.learn.manage_account.model.Rooms;
import doctorhoai.learn.manage_account.repository.EmployeesRepository;
import doctorhoai.learn.manage_account.repository.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final Mapper mapper;
    private final EmployeesRepository employeesRepository;
    private final RoomsRepository roomsRepository;

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Rooms rooms = mapper.convertToRoom(roomDto);
        Rooms roomSaved = roomsRepository.save(rooms);
        return mapper.convertToRoomDto(roomSaved);
    }

    @Override
    public RoomDto updateRoom(UUID id, RoomDto roomDto) {
        Rooms rooms = roomsRepository.findByRoomId(id).orElseThrow(RoomNotFoundException::new);
        rooms.setName(roomDto.getName());
        rooms.setLocation(roomDto.getLocation());
        Rooms roomSaved = roomsRepository.save(rooms);
        return mapper.convertToRoomDto(roomSaved);
    }

    @Override
    public RoomDto getRoomById(UUID id) {
        Rooms rooms = roomsRepository.findByRoomId(id).orElseThrow(RoomNotFoundException::new);
        RoomDto roomDto = mapper.convertToRoomDto(rooms);
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for( Employees e : rooms.getEmployees()){
            employeeDtos.add(mapper.convertToEmployeeDto(e));
        }
        roomDto.setEmployeeDtos(employeeDtos);
        return roomDto;
    }

    @Override
    public PageObject getRoomsList(RoomFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc") ){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        }else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()));
        }
        Page<Rooms> roomsPage = roomsRepository.getRoomsByFilter(
                filter.getSearch(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getRoomIds(),
                pageable
        );
        return PageObject.builder()
                .totalPage(roomsPage.getTotalPages())
                .pageNo(filter.getPageNo())
                .data(roomsPage.getContent().stream().map(mapper::convertToRoomDto).toList())
                .build();
    }

    @Override
    public void deleteRoom(UUID id) {
        List<Employees> employees = employeesRepository.getEmployeesByRoomId_RoomId(id);
        if (!employees.isEmpty()) {
            throw new BadException("Cannot delete room because it is assigned to employees");
        }
        Rooms rooms = roomsRepository.findByRoomId(id).orElseThrow(RoomNotFoundException::new);
        roomsRepository.delete(rooms);
    }

    @Override
    public List<RoomDto> getRoomByIds(List<UUID> ids) {
        List<Rooms> rooms = roomsRepository.getRoomsByIds(ids);
        return rooms.stream().map(mapper::convertToRoomDto).toList();
    }

    @Override
    public Long getTotalRoom() {

        return roomsRepository.countBy();
    }
}
