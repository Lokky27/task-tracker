package ru.srfholding.trackermodels.project_service.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import ru.srfholding.trackermodels.common_converter.JsonNodeConverter;

import java.time.OffsetDateTime;
import java.util.UUID;

import static lombok.AccessLevel.NONE;
import static ru.srfholding.trackermodels.common_constants.SchemaNames.PROJECT_SERVICE_SCHEMA;

/**
 * OUTBOX событие по Project Service
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "outbox_event", schema = PROJECT_SERVICE_SCHEMA)
public class ProjectServiceOutboxEventEntity {
    /**
     * ID outbox события
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "event_id", nullable = false, updatable = false)
    @Setter(NONE)
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
