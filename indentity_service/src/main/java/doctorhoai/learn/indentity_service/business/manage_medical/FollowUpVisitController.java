package doctorhoai.learn.indentity_service.business.manage_medical;

import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import doctorhoai.learn.indentity_service.feign.dto.medical.FollowUpVisitFilter;
import doctorhoai.learn.indentity_service.feign.manage_medical.FollowUpVisitFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("follow_up_visit")
@RequiredArgsConstructor
public class FollowUpVisitController {

    private final FollowUpVisitFeign followUpVisitFeign;

    @PostMapping("/list")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ResponseObject> getFollowUpVisitList(
            @RequestBody FollowUpVisitFilter filter
    ) {
        return followUpVisitFeign.getFollowUpVisitList(filter);
    }
}
