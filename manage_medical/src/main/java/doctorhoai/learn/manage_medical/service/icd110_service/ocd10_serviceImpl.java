package doctorhoai.learn.manage_medical.service.icd110_service;

import doctorhoai.learn.manage_medical.dto.ICD10Dto;
import doctorhoai.learn.manage_medical.dto.filter.ICD10Filter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;
import doctorhoai.learn.manage_medical.model.ICD10Codes;
import doctorhoai.learn.manage_medical.repository.ICD10Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ocd10_serviceImpl implements icd10_service {

    private final ICD10Repository icd10Repository;

    @Override
    public PageObject getICD110(ICD10Filter filter) {
        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()));
        Page<ICD10Codes> icd10CodesPage = icd10Repository.getICD10CodesByFilter(
                filter.getSearch(),
                pageable
        );
        PageObject pageObject = PageObject.builder()
                .pageNo(filter.getPageNo())
                .totalPage(icd10CodesPage.getTotalPages())
                .data(
                        icd10CodesPage.getContent().stream().map( i -> {
                            return ICD10Dto.builder()
                                    .code(i.getCode())
                                    .description(i.getDescription())
                                    .build();
                        }).toList()
                )
                .build();
        return pageObject;
    }
}
