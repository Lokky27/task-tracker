package ru.srfholding.trackerdto.task.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.task_service.constant.TaskStatusType;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * Тело ответа смена статуса задачи
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ChangeStatusTaskBody {
    /**
     * ID задачи
     */
    UUID taskID;
    /**
     * Старый статус
     */
    TaskStatusType oldStatus;
    /**
     * Новый статус
     */
    TaskStatusType newStatus;
}
