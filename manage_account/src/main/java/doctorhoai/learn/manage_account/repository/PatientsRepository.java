package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.Account.AccountStatus;
import doctorhoai.learn.manage_account.model.Patients;
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
public interface PatientsRepository extends JpaRepository<Patients, UUID> {
    /**
     * get patient
     * @param id - id patient
     * @param status - status account
     * @return - patient optional
     */
    @Query("""
        SELECT p 
        FROM patients p
        WHERE p.patientId = :id 
        AND (:status IS NULL OR p.accountId.status = :status) 
    """)
    Optional<Patients> getPatientsByPatientId(UUID id, AccountStatus status);

    /**
     * get patient list with filter
     * @param search - search
     * @param startDate - range date
     * @param endDate - range date
     * @param ids - ids list
     * @param status - status
     * @param pageable - pagination
     * @return - list patient
     */
    @Query("""
        SELECT p
        FROM patients p
        WHERE (
                    (:search) IS NULL 
                    OR p.fullName ILIKE CAST(CONCAT('%', :search, '%') as text)
                    OR p.address ILIKE CAST(CONCAT('%', :search, '%') as text) 
                    OR p.citizenId ILIKE CAST(CONCAT('%', :search, '%') as text)
                    OR p.emergencyContact ILIKE CAST(CONCAT('%', :search, '%') as text)
                    OR p.insuranceCode ILIKE CAST(CONCAT('%', :search, '%') as text)
              )
              AND (CAST(:startDate AS date) IS NULL OR p.accountId.createdAt >= :startDate)
              AND (CAST(:endDate AS date) IS NULL OR p.accountId.createdAt <= :endDate)
              AND ((:ids) IS NULL OR p.patientId IN :ids)
              AND ((:status) IS NULL OR p.accountId.status = :status)
    """)
    Page<Patients> getPatientsByFilter(
            @Param("search") String search,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("ids") List<UUID> ids,
            @Param("status") AccountStatus status,
            Pageable pageable
    );

    /**
     * get patient by phone number
     * @param phoneNumber - phone number
     * @param status - status
     * @return - patient optional
     */
    @Query("""
        SELECT p
        FROM patients p
        WHERE p.accountId.phoneNumber = :phoneNumber
        AND (:status IS NULL OR p.accountId.status = :status)
    """)
    Optional<Patients> getPatientsByPhoneAndStatus(String phoneNumber, AccountStatus status);

    Optional<Patients> findByCitizenId(String citizenId);
    Optional<Patients> findByInsuranceCode(String insuranceCode);

    List<Patients> getPatientsByPatientIdIn(List<UUID> ids);

    Optional<Patients> getPatientsByAccountId_AccountId(UUID id);

    Optional<Patients> getPatientsByAccountId_PhoneNumberAndAccountId_Status(String username, AccountStatus status);

    @Query("""
        SELECT p
        FROM patients p
        WHERE p.patientId IN :ids
        AND (
            ((:search) IS NULL 
            OR p.fullName ILIKE CAST(CONCAT('%', :search, '%') as text)
            OR p.address ILIKE CAST(CONCAT('%', :search, '%') as text) 
            OR p.citizenId ILIKE CAST(CONCAT('%', :search, '%') as text)
            OR p.emergencyContact ILIKE CAST(CONCAT('%', :search, '%') as text)
            OR p.insuranceCode ILIKE CAST(CONCAT('%', :search, '%') as text))
            AND 
            (:ids IS NULL OR p.patientId IN :ids)
        )
    """)
    List<Patients> findAllByPatientIdIn(List<UUID> ids, String search);
}
