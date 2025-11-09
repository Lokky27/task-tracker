package ru.srfholding.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Составной ключ task_tag
 */
@Getter
@Setter
@Embeddable
public class TaskTagKey implements Serializable {
    /**
     * ID задачи
     */
    @Column(name = "task_id")
    private UUID taskId;
    /**
     * Название тэга
     */
    @Column(name = "tag_name", length = 50, nullable = false)
    private String tagName;
}
