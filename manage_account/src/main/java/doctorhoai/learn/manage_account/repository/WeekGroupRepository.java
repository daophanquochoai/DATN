package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.WeekGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeekGroupRepository extends JpaRepository<WeekGroup, Integer> {
    @Query("""
        SELECT wg 
        FROM week_group wg 
        WHERE (wg.employees.employeeId = :employeeId)
        order by wg.id desc
        LIMIT  1
    """)
    Optional<WeekGroup> getWeekGroupByEmployeeId(UUID employeeId);

    @Query("""
        SELECT wg 
        FROM week_group wg
        WHERE (
            (:employees IS NULL OR wg.employees.employeeId IN (:employees))
        )
    """)
    Page<WeekGroup> getWeekGroupByFilter(
            List<UUID> employees,
            Pageable pageable
    );

    Optional<WeekGroup> getWeekGroupByIdAndEmployees_EmployeeId(Integer id, UUID employee);
}
