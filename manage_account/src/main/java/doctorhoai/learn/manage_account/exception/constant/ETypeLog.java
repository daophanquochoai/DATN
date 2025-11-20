package doctorhoai.learn.manage_account.exception.constant;

public enum ETypeLog {
    INFO("Information"),
    ERROR("Error"),
    WARNING("Warning"),
    DEBUG("Debug")
    ;
    private String message;
    private ETypeLog(final String message) {
        this.message = message;
    }
}
