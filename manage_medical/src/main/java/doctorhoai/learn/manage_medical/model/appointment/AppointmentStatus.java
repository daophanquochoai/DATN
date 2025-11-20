package doctorhoai.learn.manage_medical.model.appointment;

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
