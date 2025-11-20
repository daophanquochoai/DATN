package doctorhoai.learn.manage_medical.service.follow_up_visit;

import doctorhoai.learn.manage_medical.dto.filter.FollowUpVisitFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;
import doctorhoai.learn.manage_medical.mapper.Mapper;
import doctorhoai.learn.manage_medical.model.FollowUpVisit;
import doctorhoai.learn.manage_medical.repository.FollowUpVisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FollowUpVisitServiceImpl implements FollowUpVisitService{

    private final FollowUpVisitRepository followUpVisitRepository;
    private final Mapper mapper;

    @Override
    public Map<String, Integer> getFollowUpVisitStatistics(
            LocalDate startDate,
            LocalDate endDate
    ) {

        List<FollowUpVisit> followUpVisits = followUpVisitRepository.getFollowUpVisitByTime(startDate, endDate);

        Integer totalFollowUpVisits = !followUpVisits.isEmpty() ? followUpVisits.size() : 0;
        Integer completedFollowUpVisits = !followUpVisits.isEmpty() ? (int) followUpVisits.stream()
                .filter(i -> i.getAppointment() != null)
                .count() : 0;

        return Map.of(
                "totalFollowUpVisits", totalFollowUpVisits,
                "completedFollowUpVisits", completedFollowUpVisits
        );
    }

    @Override
    public PageObject getFollowUpVisits(FollowUpVisitFilter filter) {
        Pageable pageable;
        if( filter.getOrder().equals("desc")){
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).descending());
        }else {
            pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize(), Sort.by(filter.getSort()).ascending());
        }
        Page<FollowUpVisit> followUpVisitPage = followUpVisitRepository.getFollowUpVisitByFilter(
                filter.getPatient(),
                pageable
        );
        return PageObject.builder()
                .pageNo(filter.getPageNo())
                .totalPage(followUpVisitPage.getTotalPages())
                .data(followUpVisitPage.getContent().stream().map(mapper::convertToFollowUpVisitDto).toList())
                .build();
    }
}
