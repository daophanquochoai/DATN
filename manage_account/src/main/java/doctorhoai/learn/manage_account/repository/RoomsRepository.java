package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.Rooms;
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
public interface RoomsRepository extends JpaRepository<Rooms, UUID> {

    Optional<Rooms> findByRoomId(UUID roomId);

    @Query("""
        SELECT r
        FROM rooms r
        WHERE (
            ( cast(:search as text) IS NULL OR r.name ILIKE cast(concat('%', :search, '%') as text) ) 
            OR ( cast(:search as text) IS NULL OR r.location ILIKE cast(concat('%', :search, '%') as text) ) 
        ) 
        AND ( cast(:startDate as date) IS NULL OR r.createdAt >= (:startDate) )
        AND ( cast(:endDate as date) IS NULL OR r.createdAt <= (:endDate) )
        AND ( (:ids) IS NULL OR r.roomId IN (:ids))
    """)
    Page<Rooms> getRoomsByFilter(
            @Param("search") String search,
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("ids") List<UUID> ids,
            Pageable pageable
    );

    @Query("""
        SELECT r 
        FROM rooms r 
        WHERE (
            r.roomId IN (:ids)
        )
    """)
    List<Rooms> getRoomsByIds(List<UUID> ids);

    Long countBy();
}
