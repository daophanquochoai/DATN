package doctorhoai.learn.manage_account.service.shift;

import doctorhoai.learn.manage_account.dto.ShiftCreate;
import doctorhoai.learn.manage_account.dto.ShiftDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.ShiftFilter;
import doctorhoai.learn.manage_account.mapper.Mapper;
import doctorhoai.learn.manage_account.model.Shift;
import doctorhoai.learn.manage_account.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService{

    private final ShiftRepository shiftRepository;
    private final Mapper mapper;


    @Override
    public ShiftDto createShift(ShiftCreate shiftCreate) {
        Shift shift = Shift.builder()
                .startTime(shiftCreate.getStartTime())
                .endTime(shiftCreate.getEndTime())
                .build();
        shift = shiftRepository.save(shift);
        return mapper.convertToShiftDto(shift);
    }

    @Override
    public PageObject getShift(ShiftFilter filter) {
        Pageable pageable;
        if( filter.equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        }else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()));
        }
        Page<Shift> shifts = shiftRepository.getShiftsByFilter(pageable);

        PageObject pageObject = PageObject.builder()
                .totalPage(shifts.getTotalPages())
                .pageNo(filter.getPageNo())
                .data(shifts.getContent().stream().map(mapper::convertToShiftDto).toList())
                .build();
        return pageObject;
    }
}
