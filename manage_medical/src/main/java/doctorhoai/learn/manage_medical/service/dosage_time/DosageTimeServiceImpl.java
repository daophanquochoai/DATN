package doctorhoai.learn.manage_medical.service.dosage_time;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_medical.dto.DosageTimeDto;
import doctorhoai.learn.manage_medical.dto.filter.DosageTimeFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;
import doctorhoai.learn.manage_medical.exception.exception.DosageTimeNotFoundException;
import doctorhoai.learn.manage_medical.mapper.Mapper;
import doctorhoai.learn.manage_medical.model.DosageTimes;
import doctorhoai.learn.manage_medical.model.PerscriptionTime;
import doctorhoai.learn.manage_medical.repository.DosageTimeRepository;
import doctorhoai.learn.manage_medical.repository.PerscritionTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DosageTimeServiceImpl implements DosageTimeService {

    private final Mapper mapper;
    private final DosageTimeRepository dosageTimeRepository;
    private final PerscritionTimeRepository perscritionTimeRepository;

    @Override
    public DosageTimeDto createDosageTime(DosageTimeDto dto) {
        DosageTimes dosageTimes = mapper.convertToDosageTimes(dto);
        dosageTimes = dosageTimeRepository.save(dosageTimes);
        return mapper.convertToDosageTimeDto(dosageTimes);
    }

    @Override
    public DosageTimeDto getDosageTimeById(UUID id) {
        DosageTimes dosageTimes = dosageTimeRepository.findByTimeId(id).orElseThrow(DosageTimeNotFoundException::new);
        return mapper.convertToDosageTimeDto(dosageTimes);
    }

    @Override
    public PageObject getDosageTimes(DosageTimeFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        } else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).ascending());
        }
        Page<DosageTimes> dosageTimesPage = dosageTimeRepository.getDosageTimesByFilter(
                filter.getSearch(),
                filter.getStartDate(),
                filter.getEndDate(),
                filter.getIds(),
                pageable
        );
        return PageObject.builder()
                .pageNo(filter.getPageNo())
                .totalPage(dosageTimesPage.getTotalPages())
                .data(dosageTimesPage.getContent().stream().map(mapper::convertToDosageTimeDto).toList())
                .build();
    }

    @Override
    public DosageTimeDto updateDosageTime(UUID id, DosageTimeDto dto) {
        DosageTimes dosageTimes = dosageTimeRepository.findByTimeId(id).orElseThrow(DosageTimeNotFoundException::new);
        dosageTimes.setName(dto.getName());
        dosageTimes.setDescription(dto.getDescription());
        dosageTimes = dosageTimeRepository.save(dosageTimes);
        return mapper.convertToDosageTimeDto(dosageTimes);
    }

    @Override
    public void deleteDosageTime(UUID id) {
        List<PerscriptionTime> perscriptionTimes = perscritionTimeRepository.getPerscriptionTimeByTimeId_TimeId(id);
        if( !perscriptionTimes.isEmpty() ){
            throw new BadException("Cannot delete dosage time because it is referenced by prescription times");
        }
        DosageTimes dosageTimes = dosageTimeRepository.findByTimeId(id).orElseThrow(DosageTimeNotFoundException::new);
        dosageTimeRepository.delete(dosageTimes);
    }
}
