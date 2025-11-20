package doctorhoai.learn.manage_medical.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_medical.exception.constant.EMessageException;

public class ServiceNotFoundException extends NotFound {
    public ServiceNotFoundException() {
        super(EMessageException.SERVICE_NOT_FOUND.getMessage());
    }
}
