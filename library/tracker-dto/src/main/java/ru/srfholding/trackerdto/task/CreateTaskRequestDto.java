package ru.srfholding.trackerdto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.converter.constant.TaskType;

import java.time.LocalDate;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * Запрос на создание задачи
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class CreateTaskRequestDto {
    /**
     * Название задачи
     */
    String title;
    /**
     * Тип задачи
     */
    TaskType type;
    /**
     * Описание задачи
     */
    String description;
    /**
     * Дедлайн задачи
     */
    LocalDate deadline;
    /**
     * Назначить на
     */
    UUID assigneeId;
    /**
     * Проект
     */
    UUID projectId;
}
