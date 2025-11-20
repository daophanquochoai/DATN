package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class FollowUpVisitNotFoundException extends NotFound {
    public FollowUpVisitNotFoundException() {
        super(EMessageException.FOLLOW_UP_VISIT_NOT_FOUND.getMessage());
    }
}
