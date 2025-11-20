package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.dto.filter.ShiftFilter;
import doctorhoai.learn.manage_account.model.Shift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, UUID> {

    @Query("""
        SELECT s 
        FROM Shift s
        WHERE (
        (:ids) IS NULL OR s.id IN (:ids)
        )
    """)
    List<Shift> getShiftByIds(List<UUID> ids);

    @Query("""
        SELECT s 
        FROM Shift s 
    """)
    Page<Shift> getShiftsByFilter(
            Pageable pageable
    );
}
