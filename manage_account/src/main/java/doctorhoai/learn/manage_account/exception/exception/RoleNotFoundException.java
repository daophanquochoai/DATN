package doctorhoai.learn.manage_account.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_account.exception.constant.EMessageException;

public class RoleNotFoundException extends NotFound {
  public RoleNotFoundException() {
    super(EMessageException.ROLE_NOT_FOUND.getMessage());
  }
}
