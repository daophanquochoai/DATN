package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.Account.AccountStatus;
import doctorhoai.learn.manage_account.model.Employees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, UUID> {
    /**
     * get employee by id
     * @param employeeId - id
     * @param status - status employee
     * @return - optional employee
     */
    @Query("""
        SELECT e
        FROM employees e
        WHERE e.employeeId = :employeeId
          AND (:status IS NULL OR e.accountId.status = :status)
    """)
    Optional<Employees> findByEmployeeIdAndStatus(UUID employeeId, AccountStatus status);

    @EntityGraph(attributePaths = {"roomId", "employeeServices.services"})
    @Query("""
        SELECT DISTINCT e
        FROM employees e
        LEFT JOIN e.employeeServices es
        LEFT JOIN es.services s
        WHERE (cast(:startDate as date) IS NULL OR e.createdAt >= (:startDate))
          AND (cast(:endDate as date) IS NULL OR e.createdAt <= (:endDate))
          AND ((:ids) IS NULL OR e.employeeId IN (:ids))
          AND ((:specializations) IS NULL OR e.specializationId.specializationId IN (:specializations))
          AND ((:rooms) IS NULL OR e.roomId.roomId IN (:rooms))
          AND ((:active) IS NULL OR e.accountId.status = (:active))
          AND ((:search) IS NULL OR e.fullName ILIKE cast(CONCAT('%',:search,'%') as text))
          AND ((:service) IS NULL OR s.serviceId IN (:service))
    """)
    Page<Employees> getEmployeesByFilter(
            @Param("search") String search,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("ids") List<UUID> ids,
            @Param("specializations") List<UUID> specializations,
            @Param("rooms") List<UUID> rooms, // Changed from "room" to "rooms"
            @Param("service") List<UUID> service,
            @Param("active") AccountStatus active,
            Pageable pageable
    );

    /**
     * get employee by phone number
     * @param phone - phone number
     * @param status - status account
     * @return - data employee
     */
    @Query("""
        SELECT e 
        FROM employees e 
        WHERE e.accountId.phoneNumber = :phone 
            AND (:status IS NULL OR e.accountId.status = :status) 
    """)
    Optional<Employees> getEmployeesByPhoneAndStatus(String phone, AccountStatus status);

    /**
     * check to can remove specialization
     * @param specializationId - id specialization
     * @return - employee list
     */
    @Query("""
        SELECT e
        FROM employees e 
        WHERE e.specializationId.specializationId = :specializationId
    """)
    List<Employees> checkRemoveSpecialization(UUID specializationId);

    List<Employees> getEmployeesByRoomId_RoomId(UUID roomId);

    Optional<Employees> findByCitizenId(String citizenId);

    List<Employees> getEmployeesByEmployeeIdIn(List<UUID> ids);
    Optional<Employees> getEmployeesByEmail(String email);

    Optional<Employees> getEmployeesByAccountId_AccountId(UUID id);

    Optional<Employees> getEmployeesByEmployeeId(UUID id);

    Optional<Employees> getEmployeesByAccountId_PhoneNumberAndAccountId_Status(String phone, AccountStatus status);

    Optional<Employees> getEmployeesByEmployeeIdAndAccountId_Status(UUID employeeId, AccountStatus status);
}
