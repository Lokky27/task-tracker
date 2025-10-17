package ru.srfholding.trackermodels.task_service.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.srfholding.trackermodels.base.BaseEntity;
import ru.srfholding.trackermodels.common_converter.JsonNodeConverter;

import java.util.UUID;

import static lombok.AccessLevel.NONE;
import static ru.srfholding.trackermodels.common_constants.SchemaNames.TASK_SERVICE_SCHEMA;

/**
 * Сущность доски
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "board", schema = TASK_SERVICE_SCHEMA)
public class BoardEntity extends BaseEntity {

    /**
     * ID доски
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "board_id", nullable = false, updatable = false)
    @Setter(NONE)
    private UUID boardId;
    /**
     * ID проекта
     */
    @Column(name = "project_id", nullable = false)
    private UUID projectId;
    /**
     * Название доски
     */
    @Column(name = "board_name", nullable = false)
    private String boardName;
    /**
     * Описание
     */
    @Column(name = "description")
    private String description;
    /**
     * Конфигурация столбцов доски
     */
    @Column(name = "columns_config", nullable = false)
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode columnsConfig;
    /**
     * Доска по умолчанию (Да/Нет)
     */
    @Column(name = "is_default")
    private Boolean isDefault;
    /**
     * Доска активна (Да/Нет)
     */
    @Column(name = "is_active")
    private Boolean isActive;
}
