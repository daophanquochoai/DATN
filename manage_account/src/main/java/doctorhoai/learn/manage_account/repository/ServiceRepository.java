package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.Services;
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
public interface ServiceRepository extends JpaRepository<Services, UUID> {
    @Query("""
            SELECT s FROM Services s
            WHERE ((:ids) IS NULL OR  s.serviceId IN (:ids) )
            """)
    List<Services> getServicesByIds(List<UUID> ids);
    Optional<Services> getServicesByServiceId(UUID id);

    @Query("""
            SELECT s FROM Services s
            WHERE ( (:search) IS NULL OR s.name ILIKE cast(CONCAT('%', :search, '%') as text))
            AND ( (:startPrice) IS NULL OR s.price >= (:startPrice))
            AND ( (:endPrice) IS NULL OR s.price <= (:endPrice) )
            AND ((:ids) IS NULL OR s.serviceId IN (:ids))
            AND ( cast(:startDate as date) IS NULL OR s.createdAt >= (:startDate) )
            AND ( cast(:endDate as date) IS NULL OR s.createdAt <= (:endDate) )
            """)
    Page<Services> getServicesByFilter(
            @Param("search") String search,
            @Param("startPrice") Double startPrice,
            @Param("endPrice") Double endPrice,
            @Param("ids") List<UUID> ids,
            @Param("startDate")LocalDate startDate,
            @Param("endDate")LocalDate endDate,
            Pageable pageable
            );

    List<Services> findByServiceIdIn(List<UUID> ids);
}
