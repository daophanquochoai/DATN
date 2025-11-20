package doctorhoai.learn.manage_account.exception.exception;

import doctorhoai.learn.base_domain.exception.BadException;
import doctorhoai.learn.manage_account.exception.constant.EMessageException;

public class PasswordNotCorrect extends BadException {
    public PasswordNotCorrect() {
        super(EMessageException.PASSWORD_NOT_CORRECT.getMessage());
    }
}
