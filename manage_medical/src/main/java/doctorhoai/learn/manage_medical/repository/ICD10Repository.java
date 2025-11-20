package doctorhoai.learn.manage_medical.repository;

import doctorhoai.learn.manage_medical.model.ICD10Codes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICD10Repository extends JpaRepository<ICD10Codes, String> {

    @Query("""
        SELECT i
        FROM ICD10Codes i
        WHERE 
        ( 
        cast(:search as text) IS NULL OR LOWER(i.code) LIKE LOWER(cast(CONCAT('%', :search, '%') as text))
        OR LOWER(i.description) LIKE LOWER(cast(CONCAT('%', :search, '%') as text))
        )
    """)
    Page<ICD10Codes> getICD10CodesByFilter(
            @Param("search") String search,
            Pageable pageable
    );

    Optional<ICD10Codes> getICD10CodesByCode(String code);
}
