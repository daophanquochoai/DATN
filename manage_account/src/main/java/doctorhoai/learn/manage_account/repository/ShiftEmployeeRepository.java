package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.ShiftEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShiftEmployeeRepository extends JpaRepository<ShiftEmployee, UUID> {

    @Query("""
        SELECT s 
        FROM ShiftEmployee s 
        WHERE (
            ((:ids) IS NULL OR s.employees.employeeId IN (:ids))
            AND  
            (cast((:date) as date) IS NULL OR s.date = (:date))
        )
    """)
    Page<ShiftEmployee> getShiftEmployeeByFilter(
            @Param("ids")List<UUID> ids,
            @Param("date")LocalDate date,
            Pageable pageable
            );

    List<ShiftEmployee> getShiftEmployeeByEmployees_EmployeeIdAndDate(UUID id, LocalDate date);

    @Query("""
        SELECT s 
        FROM ShiftEmployee s 
        WHERE (
            s.id IN (:ids)
        )
    """)
    List<ShiftEmployee> getShiftEmployeeByIds(List<UUID> ids);

    @Query("""
        SELECT s 
        FROM ShiftEmployee s 
        WHERE (
            s.employees.employeeId IN (:ids)
            AND 
            (cast(:date as date) IS NULL OR s.date = :date)
        )
    """)
    List<ShiftEmployee> getShiftEmployeeByEmployeesIds(List<UUID> ids, LocalDate date);

    @Query("""
    SELECT s 
    FROM ShiftEmployee s 
    WHERE s.employees.employeeId = :id
    AND s.date >= :fromDate
""")
    List<ShiftEmployee> getShiftEmployeeByEmployees_EmployeeIdInMonth(
            UUID id,
            LocalDate fromDate
    );
}
