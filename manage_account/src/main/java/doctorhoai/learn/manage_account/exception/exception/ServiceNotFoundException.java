package doctorhoai.learn.manage_account.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_account.exception.constant.EMessageException;

public class ServiceNotFoundException extends NotFound {
    public ServiceNotFoundException() {
        super(EMessageException.SERVICE_NOT_FOUND.getMessage());
    }
}
