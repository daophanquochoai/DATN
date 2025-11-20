package doctorhoai.learn.manage_account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {

    @Column(name = "created_at")
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime updatedAt;
}
