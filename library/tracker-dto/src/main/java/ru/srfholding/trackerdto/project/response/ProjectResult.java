package ru.srfholding.trackerdto.project.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.srfholding.trackermodels.converter.constant.ProjectStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

/**
 * Тело ответа сервиса проектов
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class ProjectResult {
    /**
     * ID проекта
     */
    UUID projectId;
    /**
     * Название проекта
     */
    String projectName;
    /**
     * Описание проекта
     */
    String description;
    /**
     * Статус проекта
     */
    ProjectStatus projectStatus;
    /**
     * ID владельца
     */
    UUID ownerId;
    /**
     * Дата создания
     */
    LocalDateTime createdAt;
    /**
     * Участники проекта
     */
    List<UUID> projectMembers;
}
