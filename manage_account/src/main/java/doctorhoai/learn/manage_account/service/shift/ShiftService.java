package doctorhoai.learn.manage_account.service.shift;


import doctorhoai.learn.manage_account.dto.ShiftCreate;
import doctorhoai.learn.manage_account.dto.ShiftDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.ShiftFilter;

import java.util.List;

public interface ShiftService {

    ShiftDto createShift(ShiftCreate shiftCreate);
    PageObject getShift(ShiftFilter filter);
}
