package doctorhoai.learn.manage_account.repository;

import doctorhoai.learn.manage_account.model.notification.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, UUID> {
}
