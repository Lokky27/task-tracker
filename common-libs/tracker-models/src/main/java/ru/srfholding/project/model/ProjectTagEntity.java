package ru.srfholding.project.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.PROJECT_SERVICE_SCHEMA;

/**
 * Тэг проекта
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_tag", schema = PROJECT_SERVICE_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class ProjectTagEntity {
    /**
     * Составной первичный ключ тэгов проекта
     */
    @EmbeddedId
    private ProjectTagKey projectTagKey;
    /**
     * Проект
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @MapsId("projectId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProjectEntity projectEntity;
    /**
     * Название тэга
     */
    @Column(name = "tag_name", nullable = false, length = 50)
    private String tagName;
    /**
     * Цвет тэга
     */
    @Column(name = "tag_color", length = 7)
    private String tagColor;
    /**
     * Описание
     */
    @Column(name = "description")
    private String description;
    /**
     * Дата создания
     */
    @CreationTimestamp
    @Column(name = "created_at")
    @Setter(AccessLevel.NONE)
    private OffsetDateTime createdAt;
    /**
     * Кем создан
     */
    @CreatedBy
    @Column(name = "created_by")
    private UUID createdBy;
}
