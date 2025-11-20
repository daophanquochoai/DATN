package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.model.Units;
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
public interface UnitsRepository extends JpaRepository<Units, UUID> {
    Optional<Units> getUnitsByUnitId(UUID id);

    @Query("""
        SELECT u 
        FROM Units u 
        WHERE 
        (
            ((:search) IS NULL OR u.name ILIKE cast(CONCAT('%',:search, '%') as text)) 
            OR 
            ((:search) IS NULL OR u.description ILIKE cast(CONCAT('%',:search, '%') as text)) 
        )
        AND 
        ( 
            cast(:startDate as date) IS NULL OR u.createdAt >= (:startDate)
        )
        AND 
        ( 
            cast(:endDate as date) IS NULL OR u.createdAt <= (:endDate)
        )
        AND 
        (
            (:ids) IS NULL OR u.unitId IN (:ids)
        )
    """)
    Page<Units> getUnitsByFilter(
            @Param("search") String search,
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("ids")List<UUID> ids,
            Pageable pageable
            );

    Set<Units> getUnitsByUnitIdIn(Set<UUID> ids);
}
