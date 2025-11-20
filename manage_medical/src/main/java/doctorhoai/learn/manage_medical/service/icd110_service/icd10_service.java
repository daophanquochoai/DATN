package doctorhoai.learn.manage_medical.service.icd110_service;

import doctorhoai.learn.manage_medical.dto.filter.ICD10Filter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;

public interface icd10_service {
    PageObject getICD110(ICD10Filter filter);
}
