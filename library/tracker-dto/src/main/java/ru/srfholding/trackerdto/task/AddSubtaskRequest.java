package ru.srfholding.trackerdto.task;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * Запрос на добавление подзадачи
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class AddSubtaskRequest {
    /**
     * Заголовок задачи
     */
    @NotNull(message = "Заголовок обязателен")
    String title;
    /**
     * Описание задачи
     */
    @Size(max = 500)
    String description;
    /**
     * Дедлайн
     */
    @NotNull(message = "Поля deadline обязательно к заполнению")
    LocalDate deadline;
    /**
     * Исполнитель
     */
    @NotNull(message = "Поле Исполнитель должно быть заполнено")
    UUID assignee;
}
