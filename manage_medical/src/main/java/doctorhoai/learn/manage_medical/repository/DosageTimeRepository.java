package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.model.DosageTimes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface DosageTimeRepository extends JpaRepository<DosageTimes, UUID> {
    Optional<DosageTimes> findByTimeId(UUID id);

    @Query("""
            SELECT dt FROM DosageTimes dt
            WHERE (cast(:search as text) IS NULL OR LOWER(dt.name) LIKE LOWER(CAST(CONCAT('%', :search, '%') as text)))
            AND ( cast(:startDate as date) IS NULL OR dt.createdAt >= :startDate)
            AND ( cast(:endDate as date) IS NULL OR dt.createdAt <= :endDate)
            AND (:ids IS NULL OR dt.timeId IN :ids)
    """)
    Page<DosageTimes> getDosageTimesByFilter(
            @Param("search") String search,
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("ids") List<UUID> ids,
            Pageable pageable
            );
    Set<DosageTimes> getDosageTimesByTimeIdIn(Set<UUID> ids);
}
