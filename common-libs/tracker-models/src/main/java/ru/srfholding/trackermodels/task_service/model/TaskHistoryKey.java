package ru.srfholding.trackermodels.task_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static lombok.AccessLevel.NONE;

/**
 * Составной ключ истории изменений задачи
 */
@Getter
@Setter
@Embeddable
@EntityListeners(AuditingEntityListener.class)
public class TaskHistoryKey implements Serializable {
    /**
     * ID исторической записи
     */
    @GeneratedValue(generator = "UUID")
    @Column(name = "history_id", nullable = false, updatable = false, insertable = false)
    @Setter(NONE)
    private UUID historyId;
    /**
     * Дата создания
     */
    @Column(name = "changed_at")
    @CreatedDate
    private OffsetDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskHistoryKey that = (TaskHistoryKey) o;
        return Objects.equals(historyId, that.historyId) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historyId, createdAt);
    }
}
