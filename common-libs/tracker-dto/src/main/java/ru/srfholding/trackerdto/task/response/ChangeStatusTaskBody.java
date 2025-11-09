package ru.srfholding.trackerdto.task.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.srfholding.task.constant.TaskStatusType;

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
