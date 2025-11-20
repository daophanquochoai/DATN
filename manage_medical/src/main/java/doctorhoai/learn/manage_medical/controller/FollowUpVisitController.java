package doctorhoai.learn.manage_medical.controller;


import doctorhoai.learn.manage_medical.controller.constant.EMessageResponse;
import doctorhoai.learn.manage_medical.dto.ResponseObject;
import doctorhoai.learn.manage_medical.dto.filter.FollowUpVisitFilter;
import doctorhoai.learn.manage_medical.service.follow_up_visit.FollowUpVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("follow_up_visit")
@RequiredArgsConstructor
public class FollowUpVisitController {

    private final FollowUpVisitService followUpVisitService;

    @PostMapping("/list")
    public ResponseEntity<ResponseObject> getFollowUpVisitList(
            @RequestBody FollowUpVisitFilter filter
            ) {
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message(EMessageResponse.GET_DATA_SUCCESSFULLY.getMessage())
                        .data(followUpVisitService.getFollowUpVisits(filter))
                        .build()
        );
    }
}
