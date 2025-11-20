package doctorhoai.learn.manage_medical.service.units;

import doctorhoai.learn.manage_medical.dto.UnitsDto;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;
import doctorhoai.learn.manage_medical.dto.filter.UnitFilter;

import java.util.UUID;

public interface UnitService {
    UnitsDto createUnit(UnitsDto unitsDto);
    UnitsDto getUnitById(UUID id);
    UnitsDto updateUnit(UUID id, UnitsDto unitsDto);
    void deleteUnit(UUID id);
    PageObject getListUnit(UnitFilter filter);
}
