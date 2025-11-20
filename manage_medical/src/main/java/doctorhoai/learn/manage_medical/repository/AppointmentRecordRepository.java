package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.model.AppointmentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRecordRepository extends JpaRepository<AppointmentRecord, UUID> {
    Optional<AppointmentRecord> getAppointmentRecordByAppointment_AppointmentId(UUID id);
}
