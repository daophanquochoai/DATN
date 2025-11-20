package doctorhoai.learn.indentity_service.feign.dto.medical;

public enum AppointmentStatus {
    PAYMENT("payment"),
    COMPLETE("complete"),
    CREATE("create"),
    ;
    private String message;
    AppointmentStatus(String message) {
        this.message = message;
    }
}
