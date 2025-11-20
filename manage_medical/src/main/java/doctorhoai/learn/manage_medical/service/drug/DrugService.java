package doctorhoai.learn.manage_medical.service.drug;

import doctorhoai.learn.manage_medical.dto.DrugDto;
import doctorhoai.learn.manage_medical.dto.filter.DrugFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;

import java.util.UUID;

public interface DrugService {
    DrugDto createDrug(DrugDto drugDto);
    DrugDto updateDrug(UUID id, DrugDto drugDto);
    DrugDto getDrugById(UUID id);
    PageObject getDrugPage(DrugFilter filter);
    void deleteByDrug(UUID id);
}
