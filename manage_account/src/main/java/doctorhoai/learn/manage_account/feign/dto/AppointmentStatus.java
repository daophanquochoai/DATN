package doctorhoai.learn.manage_account.feign.dto;

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
