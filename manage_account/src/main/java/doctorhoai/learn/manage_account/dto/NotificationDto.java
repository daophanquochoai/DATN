package doctorhoai.learn.manage_account.dto;

import doctorhoai.learn.manage_account.model.notification.RecordType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotificationDto {
    private UUID notificationId;
    private String message;
    private RecordType recordType;
    private boolean isRead;
    private PatientsDto patientsDto;
    private UUID appointmentId;
}
