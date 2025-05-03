package ru.srfholding.trackerdto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * Запрос на назначение задачи на исполнителя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class AssignTaskRequest {
    /**
     * UUID исполнителя
     */
    UUID assigneeId;
}
