package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.dto.filter.DrugFilter;
import doctorhoai.learn.manage_medical.model.Drug;
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
public interface DrugRepository extends JpaRepository<Drug, UUID> {
    Optional<Drug> getDrugByDrugId(UUID uuid);

    @Query("""
        SELECT d 
        FROM Drug d 
        WHERE (
            (:search) IS NULL OR d.name ILIKE cast(CONCAT('%',:search,'%') as text)
            OR 
            (:search) IS NULL OR d.genericName ILIKE cast(CONCAT('%',:search,'%') as text)
            OR 
            (:search) IS NULL OR d.description ILIKE cast(CONCAT('%',:search,'%') as text)
        )
        AND 
        ( cast(:startDate as date) IS NULL OR d.createdAt >= (:startDate) )
        AND 
         ( cast(:endDate as date) IS NULL OR d.createdAt <= (:endDate) )
        AND 
        ( (:ids) IS NULL OR d.drugId IN (:ids) ) 
    """)
    Page<Drug> getDrugByFilter(
            @Param("search") String search,
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("ids")List<UUID> ids,
            Pageable pageable
            );

    Set<Drug> getDrugByDrugIdIn(Set<UUID> ids);


}
