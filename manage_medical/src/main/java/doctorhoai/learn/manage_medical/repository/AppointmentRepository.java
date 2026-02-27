package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.dto.request.ShiftCount;
import doctorhoai.learn.manage_medical.model.AppointmentRecord;
import doctorhoai.learn.manage_medical.model.appointment.Appointment;
import doctorhoai.learn.manage_medical.model.appointment.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Set<Appointment> getAppointmentByAppointmentIdIn(Set<UUID> ids);

    @Query("""
        SELECT a 
        FROM Appointment a 
        WHERE (
            ( (:shiftIds) IS NULL OR a.shiftId IN (:shiftIds) ) AND 
            ( (:patientIds) IS NULL OR a.patientId IN (:patientIds) ) AND 
            ( (:status) IS NULL OR a.status IN (:status) )  
        )
    """)
    Page<Appointment> getAppointmentByFilter(
            @Param("shiftIds") List<UUID> shiftIds,
            @Param("patientIds") List<UUID> patientIds,
            @Param("status") List<AppointmentStatus> status,
            Pageable pageable
    );

    Optional<Appointment> getAppointmentByAppointmentId(UUID id);

    @Query("""
    SELECT a 
    FROM Appointment a 
    WHERE 
        a.patientId = :patientId
        AND a.serviceId = :serviceId
        AND
        (
        :appointmentStatus IS NULL OR a.status IN :appointmentStatus
        )
""")
    List<Appointment> getAppointmentWasBook(
            @Param("patientId") UUID patientId,
            @Param("serviceId") UUID serviceId,
            @Param("appointmentStatus") List<AppointmentStatus> appointmentStatus
    );

    @Query("""
        SELECT a 
        FROM Appointment a
        WHERE (
            a.shiftId = :shiftId AND 
            ( :status IS NULL OR a.status IN :status)
        )
    """)
    List<Appointment> getAppointmentByShiftId(UUID shiftId, List<AppointmentStatus> status);

    @Query("""
        SELECT a 
        FROM Appointment a
        WHERE (
            a.patientId = :patientId AND 
            a.shiftId IN (:shiftId) AND 
            a.serviceId = :serviceId AND 
            ( :status IS NULL OR a.status IN :status)
        )
    """)
    List<Appointment> getAppointmentBooked(
            UUID patientId,
            UUID serviceId,
            List<UUID> shiftId,
            List<AppointmentStatus> status
    );

    @Query("""
        SELECT a.shiftId, COUNT(a.appointmentId)
        FROM Appointment a
        WHERE (:shiftIds IS NULL OR a.shiftId IN :shiftIds)
        AND ( :status IS NULL OR a.status IN :status)
        GROUP BY a.shiftId
    """)
    List<Object[]> countAppointmentByShiftIdIn(
            @Param("shiftIds") Set<UUID> shiftIds,
            @Param("status") List<AppointmentStatus> status
    );

    @Query("""
        SELECT a 
        FROM Appointment a
        WHERE
        (
            a.patientId = :patientId AND 
            a.serviceId = :serviceId AND 
            ( :shiftIds IS NULL OR a.shiftId IN (:shfiftids)) AND 
            ( :status IS NULL OR a.status IN :status )
        )
    """)
    List<Appointment> getAppointmentByPatientIdAndServiceIdAndShiftIdIn(
            UUID patientId,
            UUID serviceId,
            List<UUID> shiftIds,
            List<AppointmentStatus> status
    );

    @Query("""
        SELECT a 
        FROM Appointment a
        WHERE 
            ( :status IS NULL OR a.status IN :status ) 
            AND
            ( cast(:startDate as timestamp ) IS NULL OR a.createdAt >= CAST(:startDate AS timestamp ) ) 
            AND
            ( cast(:endDate as timestamp ) IS NULL OR a.createdAt < CAST(FUNCTION('DATEADD', DAY, 1, :endDate) AS timestamp) )
    """)
    List<Appointment> getAppointmentByServiceIdAndStatusInAndAppointmentDateBetween(
            LocalDate startDate,
            LocalDate endDate,
            List<AppointmentStatus> status
    );

    @Query("""
        SELECT CAST(a.createdAt AS LocalDate), COUNT(a) 
        FROM Appointment a
        WHERE 
            ( cast(:startDate as timestamp ) IS NULL OR a.createdAt >= CAST(:startDate AS timestamp ) ) 
            AND
            ( cast(:endDate as timestamp ) IS NULL OR a.createdAt < CAST(FUNCTION('DATEADD', DAY, 1, :endDate) AS timestamp) )
        GROUP BY CAST(a.createdAt AS LocalDate)
        ORDER BY CAST(a.createdAt AS LocalDate)
    """)
    List<Object[]> countAppointmentsByDate(
            LocalDate startDate,
            LocalDate endDate,
            List<AppointmentStatus> status
    );

    @Query("""
        SELECT a 
        FROM Appointment a
        WHERE
            :shiftIds IS NULL OR a.shiftId IN :shiftIds
    """)
    List<Appointment> getAppointmentsByShiftIds(
            List<UUID> shiftIds
    );

    @Query("""
        SELECT ar
        FROM Appointment a
        JOIN AppointmentRecord ar ON a.appointmentId = ar.appointment.appointmentId
        WHERE
            a.shiftId IN :shiftIds
            AND ar.icd10Codes.code = :icd10Id
        ORDER BY ar.createdAt DESC
        LIMIT 1
    """)
    Optional<AppointmentRecord> getOldAppointment(List<UUID> shiftIds, String icd10Id);

    List<Appointment> getAppointmentByShiftId(UUID shiftId);

    @Query(value = """
    SELECT a.shift_id 
    FROM appointments a 
    JOIN appointment_records ar ON a.appointment_id = ar.appointment_id
    WHERE ( CAST(:startDate AS date) IS NULL OR DATE(a.created_at) >= CAST(:startDate AS date))
      AND (CAST(:endDate AS date) IS NULL OR DATE(a.created_at) <= CAST(:endDate AS date))
""", nativeQuery = true)
    List<UUID> getShiftCountByFilter(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}