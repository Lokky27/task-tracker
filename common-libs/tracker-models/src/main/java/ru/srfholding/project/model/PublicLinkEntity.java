package ru.srfholding.project.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.srfholding.project.constant.ProjectAccessLevel;
import ru.srfholding.project.converter.ProjectAccessLevelConverter;

import java.time.OffsetDateTime;
import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.PROJECT_SERVICE_SCHEMA;

/**
 * Сущность публичной ссылки
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "public_link", schema = PROJECT_SERVICE_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class PublicLinkEntity {
    /**
     * ID публичной ссылки
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "link_id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private UUID publicLinkId;
    /**
     * ID проекта
     */
    @Column(name = "project_id", nullable = false, insertable = false, updatable = false)
    private UUID projectId;
    /**
     * Проект
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProjectEntity projectEntity;
    /**
     * Токен
     */
      @Column(name = "token", nullable = false)
      private String token;
    /**
     * Уровень доступа
     */
    @Column(name = "access_level_code")
    @Convert(converter = ProjectAccessLevelConverter.class)
    private ProjectAccessLevel accessLevel;
    /**
     * Хэш пароля
     */
    @Column(name = "password_hash")
    private String passwordHash;
    /**
     * Максимальных использований
     */
    @Column(name = "max_uses")
    private Integer maxUses;
    /**
     * Количество использований
     */
    @Column(name = "used_count")
    private Integer usedCount;
    /**
     * Дата истечения токена
     */
    @Column(name = "expires_at")
    private OffsetDateTime offsetDateTime;
    /**
     * Дата создания
     */
    @Column(name = "created_at")
    @CreationTimestamp
    private OffsetDateTime createdAt;
    /**
     * Кем создано
     */
    @Column(name = "created_by")
    @CreatedBy
    private UUID createdBy;
    /**
     * Дата последнего входа
     */
    @Column(name = "last_accessed_at")
    private OffsetDateTime lasAccessedAt;
}
