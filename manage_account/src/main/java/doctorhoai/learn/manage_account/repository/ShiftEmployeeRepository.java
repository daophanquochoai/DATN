package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.dto.EmployeeShiftCounter;
import doctorhoai.learn.manage_account.model.ShiftEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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
            ((:ids) IS NULL OR s.shiftDayEmployee.employees.employeeId IN (:ids))
            AND  
            (cast((:date) as date) IS NULL OR s.shiftDayEmployee.date = (:date))
        )
    """)
    Page<ShiftEmployee> getShiftEmployeeByFilter(
            @Param("ids")List<UUID> ids,
            @Param("date")LocalDate date,
            Pageable pageable
            );

    List<ShiftEmployee> getShiftEmployeeByShiftDayEmployee_Employees_EmployeeIdAndShiftDayEmployee_Date(UUID id, LocalDate date);

    @Query("""
        SELECT s 
        FROM ShiftEmployee s 
        WHERE (
            s.id IN (:ids)
        )
    """)
    List<ShiftEmployee> getShiftEmployeeByIds(List<UUID> ids);

    @EntityGraph()
    @Query("""
        SELECT s 
        FROM ShiftEmployee s 
        WHERE (
            s.shiftDayEmployee.employees.employeeId IN (:ids)
            AND 
            (cast(:date as date) IS NULL OR s.shiftDayEmployee.date = :date)
        )
    """)
    List<ShiftEmployee> getShiftEmployeeByEmployeesIds(List<UUID> ids, LocalDate date);

    @Query("""
    SELECT s 
    FROM ShiftEmployee s 
    WHERE s.shiftDayEmployee.employees.employeeId = :id
    AND s.shiftDayEmployee.date >= :fromDate
""")
    List<ShiftEmployee> getShiftEmployeeByEmployees_EmployeeIdInMonth(
            UUID id,
            LocalDate fromDate
    );

    @Query("""
    SELECT new doctorhoai.learn.manage_account.dto.EmployeeShiftCounter(
        s.shiftDayEmployee.employees,
        COUNT(s)
    ) 
    FROM ShiftEmployee s 
    WHERE s.id IN (:ids)
    GROUP BY s.shiftDayEmployee.employees
    ORDER BY COUNT(s) DESC
""")
    List<EmployeeShiftCounter> countShiftsByEmployeeId(
            @Param("ids") List<UUID> ids
    );
}
