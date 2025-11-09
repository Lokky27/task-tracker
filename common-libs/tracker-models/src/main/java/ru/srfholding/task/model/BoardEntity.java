package ru.srfholding.task.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import ru.srfholding.common.base.BaseEntity;
import ru.srfholding.common.converter.JsonNodeConverter;

import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.TASK_SERVICE_SCHEMA;


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
    @Setter(AccessLevel.NONE)
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
