package doctorhoai.learn.manage_medical.service.follow_up_visit;

import doctorhoai.learn.manage_medical.dto.filter.FollowUpVisitFilter;
import doctorhoai.learn.manage_medical.dto.filter.PageObject;

import java.time.LocalDate;
import java.util.Map;

public interface FollowUpVisitService {
    Map<String, Integer> getFollowUpVisitStatistics(LocalDate startDate, LocalDate endDate);
    PageObject getFollowUpVisits(FollowUpVisitFilter filter);
}
