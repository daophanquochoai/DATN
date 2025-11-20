package doctorhoai.learn.manage_medical.service.dosage_time;

import doctorhoai.learn.manage_medical.dto.DosageTimeDto;
import doctorhoai.learn.manage_medical.dto.filter.DosageTimeFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;

import java.util.UUID;

public interface DosageTimeService {

    DosageTimeDto createDosageTime(DosageTimeDto dto);
    DosageTimeDto getDosageTimeById(UUID id);
    PageObject getDosageTimes(DosageTimeFilter filter);
    DosageTimeDto updateDosageTime(UUID id, DosageTimeDto dto);
    void deleteDosageTime(UUID id);
}
