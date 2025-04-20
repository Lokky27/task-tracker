package ru.srfholding.trackerdto.project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * Запрос на создание проекта
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class CreateProjectRequest {
    /**
     * Название проекта
     */
    @NotBlank(message = "Название проекта не может быть пустым")
    String name;
    /**
     * Описание проекта
     */
    @Size(max = 500, message = "Описание проекта должно быть не длиннее 500 символов")
    String description;
    /**
     * Владелец проекта
     */
    @NotNull(message = "ownerId обязателен")
    UUID ownerId;
}
