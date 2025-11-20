package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.model.MealRelation;
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
public interface MealRelationRepository extends JpaRepository<MealRelation, UUID> {
    Optional<MealRelation> getMealRelationByRelationsId(UUID id);

    @Query("""
            SELECT mr FROM MealRelation mr
            WHERE ( cast(:search as text) IS NULL OR LOWER(mr.name) LIKE LOWER(cast(CONCAT('%', :search, '%') as text)))
            AND ( cast(:startDate as date) IS NULL OR mr.createdAt >= (:startDate))
            AND ( cast(:endDate as date) IS NULL OR mr.createdAt <= (:endDate) )
            AND ( (:ids) IS NULL OR mr.relationsId IN (:ids))
            """)
    Page<MealRelation> getMealRelationByFilter(
            @Param("search") String search,
            @Param("startDate")LocalDate startDate,
            @Param("endDate")LocalDate endDate,
            @Param("ids") List<UUID> ids,
            Pageable pageable
            );
    Set<MealRelation> getMealRelationByRelationsIdIn(Set<UUID> ids);
}
