package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.model.FollowUpVisit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowUpVisitRepository extends JpaRepository<FollowUpVisit, UUID> {
    @Query("""
        SELECT f FROM FollowUpVisit f
        JOIN FETCH f.appointmentRecord ar
        JOIN FETCH ar.appointment a
        WHERE f.followUpId = :id
    """)
    Optional<FollowUpVisit> getFollowUpVisitByFollowUpId(UUID id);
    @Query("""
        SELECT f 
        FROM FollowUpVisit f 
        WHERE (
            (cast(:startDate as date) IS NULL OR f.followUpDate >= (:startDate)) 
            AND 
            (cast(:endDate as date) IS NULL OR f.followUpDate <= (:endDate))
        )
    """)
    List<FollowUpVisit> getFollowUpVisitByTime(LocalDate startDate, LocalDate endDate);

    @Query("""
        SELECT f 
        FROM FollowUpVisit f 
        WHERE (
            f.appointmentRecord.appointment.patientId = :patientId
            AND 
            f.appointment IS NULL
        )
    """)
    Page<FollowUpVisit> getFollowUpVisitByFilter(
            UUID patientId,
            Pageable pageable
    );
}
