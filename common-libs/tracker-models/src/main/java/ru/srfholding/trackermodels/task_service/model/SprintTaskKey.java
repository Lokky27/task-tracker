package ru.srfholding.trackermodels.task_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Составной ключ sprint_task
 */
@Embeddable
@Getter
@Setter
public class SprintTaskKey implements Serializable {
    /**
     * ID спринта
     */
    @Column(name = "sprint_id")
    private UUID sprintId;
    /**
     * ID задачи
     */
    @Column(name = "task_id")
    private UUID taskId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SprintTaskKey that = (SprintTaskKey) o;
        return Objects.equals(sprintId, that.sprintId) && Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sprintId, taskId);
    }
}
