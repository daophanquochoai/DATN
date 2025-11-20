package doctorhoai.learn.manage_account.service.specialization;

import doctorhoai.learn.manage_account.dto.SpecializationsDto;
import doctorhoai.learn.manage_account.dto.filter.PageObject;
import doctorhoai.learn.manage_account.dto.filter.SpecializationFilter;

import java.util.UUID;

public interface SpecializationService {
    SpecializationsDto createSpecializations(SpecializationsDto specializationsDto);
    SpecializationsDto getSpecializations(UUID specializationId);
    void deleteSpecializations(UUID specializationId);
    SpecializationsDto updateSpecializations(UUID id ,SpecializationsDto specializationsDto);
    PageObject getSpecializations(SpecializationFilter filter);
}
