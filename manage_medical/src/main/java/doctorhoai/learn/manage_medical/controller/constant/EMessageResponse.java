package doctorhoai.learn.manage_medical.controller.constant;

import lombok.Getter;

@Getter
public enum EMessageResponse {
    CREATE_DATA_SUCCESSFULLY("Create data successfully"),
    UPDATE_DATA_SUCCESSFULLY("Update data successfully"),
    DELETE_DATA_SUCCESSFULLY("Delete data successfully"),
    GET_DATA_SUCCESSFULLY("Get data successfully"),

    ;
    private String message;
    EMessageResponse(String message) {
        this.message = message;
    }
}
