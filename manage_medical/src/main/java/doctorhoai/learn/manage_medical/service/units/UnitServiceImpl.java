package doctorhoai.learn.manage_medical.service.units;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_medical.dto.UnitsDto;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;
import doctorhoai.learn.manage_medical.dto.filter.UnitFilter;
import doctorhoai.learn.manage_medical.exception.UnitNotFoundException;
import doctorhoai.learn.manage_medical.mapper.Mapper;
import doctorhoai.learn.manage_medical.model.Perscriptions;
import doctorhoai.learn.manage_medical.model.Units;
import doctorhoai.learn.manage_medical.repository.PerscriptionRepository;
import doctorhoai.learn.manage_medical.repository.UnitsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitsRepository unitsRepository;
    private final PerscriptionRepository perscriptionRepository;
    private final Mapper mapper;

    @Override
    @Transactional
    public UnitsDto createUnit(UnitsDto unitsDto) {
        Units unit = mapper.convertToUnits(unitsDto);
        unit = unitsRepository.save(unit);
        return mapper.convertToUnitsDto(unit);
    }

    @Override
    public UnitsDto getUnitById(UUID id) {
        Units unit = unitsRepository.getUnitsByUnitId(id)
                .orElseThrow(UnitNotFoundException::new);
        return mapper.convertToUnitsDto(unit);
    }

    @Override
    @Transactional
    public UnitsDto updateUnit(UUID id, UnitsDto unitsDto) {
        Units unit = unitsRepository.getUnitsByUnitId(id).orElseThrow(UnitNotFoundException::new);
        unit.setName(unitsDto.getName());
        unit.setDescription(unitsDto.getDescription());
        unit = unitsRepository.save(unit);
        return mapper.convertToUnitsDto(unit);
    }

    @Override
    @Transactional
    public void deleteUnit(UUID id) {
        List<Perscriptions> perscriptions = perscriptionRepository.getPerscriptionsByUnitDosageId_UnitId(id);
        if (!perscriptions.isEmpty()) {
            throw new BadException("Cannot delete unit because it is associated with existing prescriptions.");
        }
        Units unit = unitsRepository.getUnitsByUnitId(id).orElseThrow(UnitNotFoundException::new);
        unitsRepository.delete(unit);
    }

    @Override
    public PageObject getListUnit(UnitFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        } else{
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).ascending());
        }
        Page<Units> unitsPage = unitsRepository.getUnitsByFilter(
            filter.getSearch(),
            filter.getStartDate(),
            filter.getEndDate(),
            filter.getIds(),
            pageable
        );
        return PageObject.builder()
                .pageNo(filter.getPageNo())
                .totalPage(unitsPage.getTotalPages())
                .data(unitsPage.getContent().stream().map(mapper::convertToUnitsDto).toList())
                .build();
    }


}
