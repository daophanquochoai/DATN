package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.Specializations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpecializationsRepository extends JpaRepository<Specializations, UUID> {
    Optional<Specializations> findBySpecializationId(UUID specializationId);

    @Query("""
        SELECT s 
        FROM specializations s
        WHERE (
            (CAST(:search as text) IS NULL OR s.name ILIKE CAST(CONCAT('%', :search, '%') as text))
            OR (CAST(:search as text) IS NULL OR s.description ILIKE CAST(CONCAT('%', :search, '%') as text))
        )
        AND ( cast(:startDate as date) IS NULL OR s.createdAt >= (:startDate))
        AND ( cast(:endDate as date) IS NULL OR s.createdAt <= (:endDate) )
        AND ( (:ids) IS NULL OR s.specializationId IN (:ids) )
    """)
    Page<Specializations> findSpecializationsByFilter(
            @Param("search") String search,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("ids") List<UUID> ids,
            Pageable pageable
    );
}
