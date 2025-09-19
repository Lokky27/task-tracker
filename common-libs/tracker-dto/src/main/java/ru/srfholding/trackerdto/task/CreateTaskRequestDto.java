package ru.srfholding.trackerdto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Заголовок задачи не может быть пустым")
    String title;
    /**
     * Тип задачи
     */
    @NotNull(message = "Тип задачи должен быть указан")
    TaskType type;
    /**
     * Описание задачи
     */
    @Size(max = 500, message = "Описание задачи должно быть не длиннее 500 символов")
    String description;
    /**
     * Дедлайн задачи
     */
    @NotNull(message = "Дедлайн задачи обязателен")
    LocalDate deadline;
    /**
     * Назначить на
     */
    @NotNull(message = "Не указан исполнитель")
    UUID assigneeId;
    /**
     * Проект
     */
    @NotNull(message = "Задача не может быть создана без привязки к проекту")
    UUID projectId;
}
