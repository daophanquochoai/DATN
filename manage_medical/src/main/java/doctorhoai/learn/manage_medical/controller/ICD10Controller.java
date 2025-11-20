package doctorhoai.learn.manage_medical.controller;

import doctorhoai.learn.manage_medical.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.filter.ICD10Filter;
import doctorhoai.learn.manage_medical.service.icd110_service.icd10_service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/icd10")
@RequiredArgsConstructor
public class ICD10Controller {

    private final icd10_service icd10Service;

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getICD10(
            @RequestBody ICD10Filter filter
            )
    {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(icd10Service.getICD110(filter))
                        .build()
        );
    }

}
