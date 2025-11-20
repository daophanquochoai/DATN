package doctorhoai.learn.manage_account.controller.constant;

import lombok.Getter;

@Getter
public enum EMessageResponse {
    SAVE_DATA_SUCCESSFUL("Save data successful"),
    GET_DATA_SUCCESSFUL("Get data successful"),
    UPDATE_DATA_SUCCESSFUL("Update data successful"),
    DELETE_DATA_SUCCESSFUL("Delete data successful"),
    ;
    private final String message;
    private EMessageResponse(String message) { this.message = message; }
}
