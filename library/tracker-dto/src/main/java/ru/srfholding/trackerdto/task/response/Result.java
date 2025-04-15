package ru.srfholding.trackerdto.task.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.converter.constant.PriorityType;
import ru.srfholding.trackermodels.converter.constant.StatusType;
import ru.srfholding.trackermodels.converter.constant.TaskType;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * Тело ответа
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class Result {
    /**
     * ID задачи
     */
    UUID taskId;
    /**
     * Название задачи
     */
    String title;
    /**
     * Описание задачи
     */
    String description;
    /**
     * Дата выполнения задачи
     */
    LocalDate deadline;
    /**
     * Статус задачи
     */
    StatusType statusCode;
    /**
     * Тип задачи
     */
    TaskType taskTypeCode;
    /**
     * Приоритет
     */
    PriorityType priority;
    /**
     * Проект к которому принадлежит задача
     */
    UUID projectId;
    /**
     * Исполнитель задачи
     */
    UUID assigneeId;
    /**
     * Создатель задачи
     */
    UUID reporterId;
    /**
     * Дата создания задачи
     */
    OffsetDateTime createdAt;
    /**
     * Дата обновления задачи
     */
    OffsetDateTime updatedAt;
}
