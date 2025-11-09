package ru.srfholding.userdetails.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.srfholding.common.converter.JsonNodeConverter;

import java.time.OffsetDateTime;
import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.USER_DETAILS_SERVICE_SCHEMA;

/**
 * OUTBOX события для user_details_service
 */
@Getter
@Setter
@Entity
@Table(name = "outbox_event", schema = USER_DETAILS_SERVICE_SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserDetailsOutboxEventEntity {
    /**
     * ID outbox события
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "event_id", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private UUID eventId;
    /**
     * Тип события
     */
    @Column(name = "event_type", length = 100)
    private String eventType;
    /**
     * Тип агрегации
     */
    @Column(name = "aggregate_type", length = 50)
    private String aggregateType;
    /**
     * ID агрегации
     */
    @Column(name = "aggregate_id")
    private UUID aggregateId;
    /**
     * Данные события
     */
    @Column(name = "event_data", columnDefinition = "jsonb")
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode eventData;
    /**
     * Дата создания
     */
    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
    /**
     * Дата выполнения
     */
    @Column(name = "processed_at")
    private OffsetDateTime processedAt;
    /**
     * Количество попыток
     */
    @Column(name = "retry_count")
    private Integer retryCount;

}
