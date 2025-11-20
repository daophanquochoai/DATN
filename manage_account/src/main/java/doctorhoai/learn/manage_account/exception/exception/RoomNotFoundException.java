package doctorhoai.learn.manage_account.exception.exception;

import doctorhoai.learn.base_domain.exception.NotFound;
import doctorhoai.learn.manage_account.exception.constant.EMessageException;

public class RoomNotFoundException extends NotFound {
    public RoomNotFoundException() {
        super(EMessageException.ROOM_NOT_FOUND.getMessage());
    }
}
