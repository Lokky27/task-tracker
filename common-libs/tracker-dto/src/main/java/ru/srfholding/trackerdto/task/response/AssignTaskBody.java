package ru.srfholding.trackerdto.task.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * Тело ответа перевода задачи на исполнителя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class AssignTaskBody {
    /**
     * UUID задачи
     */
    UUID taskId;
    /**
     * UUID исполнителя
     */
    UUID assigneeId;
    /**
     * Предыдущий исполнитель
     */
    UUID oldAssignee;
}
