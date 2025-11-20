package doctorhoai.learn.manage_medical.service.appointment_records;

import doctorhoai.learn.manage_medical.dto.AppointmentRecordDto;
import doctorhoai.learn.manage_medical.dto.request.AppointmentRecordCreate;

import java.util.UUID;

public interface AppointRecordService {
    AppointmentRecordDto createAppointmentRecord(AppointmentRecordCreate appointmentRecordCreate);
    AppointmentRecordDto getAppointmentById(UUID id);
}
