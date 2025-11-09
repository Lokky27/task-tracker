package ru.srfholding.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Составной первичный ключ тэга проета
 */
@Getter
@Setter
@Embeddable
public class ProjectTagKey implements Serializable {
    /**
     * ID тега
     */
    @GeneratedValue(generator = "UUID")
    @Column(name = "tag_id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private UUID tagId;
    /**
     * ID проекта
     */
    @Column(name = "project_id", nullable = false)
    private UUID projectId;
}
