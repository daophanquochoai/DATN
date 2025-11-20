package doctorhoai.learn.indentity_service.business.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.ICD10Filter;
import doctorhoai.learn.indentity_service.feign.manage_medical.ICD10Feign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/icd10")
@RequiredArgsConstructor
public class ICD10Controller {

    private final ICD10Feign icd10Feign;

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getICD10(
            @RequestBody ICD10Filter filter
    ){
        return icd10Feign.getICD10(filter);
    }

}
