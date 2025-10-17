package ru.srfholding.trackermodels.project_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Составной первичный ключ участника проекта
 */
@Getter
@Setter
@Embeddable
public class ProjectMemberKey implements Serializable {
    /**
     * ID проекта
     */
    @Column(name = "project_id", nullable = false)
    private UUID projectId;
    /**
     * ID участника проекта
     */
    @Column(name = "user_id", nullable = false)
    private UUID userId;
}
