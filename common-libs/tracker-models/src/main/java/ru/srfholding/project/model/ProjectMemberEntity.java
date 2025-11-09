package ru.srfholding.project.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.srfholding.common.converter.JsonNodeConverter;
import ru.srfholding.project.constant.ProjectMemberRole;
import ru.srfholding.project.converter.ProjectMemberRoleConverter;

import java.time.OffsetDateTime;
import java.util.UUID;

import static ru.srfholding.common.constants.SchemaNames.PROJECT_SERVICE_SCHEMA;

/**
 * Сущность участника проекта
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_member", schema = PROJECT_SERVICE_SCHEMA)
public class ProjectMemberEntity {
    /**
     * Составной первичный ключ участника проекта
     */
    @EmbeddedId
    private ProjectMemberKey projectMemberKey;
    /**
     * Проект
     */
    @MapsId("project_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private ProjectEntity project;
    /**
     * Роль в команде
     */
    @Column(name = "role_code")
    @Convert(converter = ProjectMemberRoleConverter.class)
    private ProjectMemberRole projectMemberRole;
    /**
     * Дата включения в проект
     */
    @Column(name = "join_at")
    @CreationTimestamp
    private OffsetDateTime joinAt;
    /**
     * Дата исключения из проекта
     */
    @Column(name = "left_at")
    private OffsetDateTime leftAt;
    /**
     * Кем приглашен в проект
     */
    @Column(name = "invited_by")
    private UUID invitedBy;
    /**
     * Дата принятия приглашения в проект
     */
    @Column(name = "invitation_accepted_by")
    private OffsetDateTime invitationAcceptedBy;
    /**
     * Кастомные разрешения
     */
    @Column(name = "custom_permissions", columnDefinition = "jsonb")
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode customPermissions;
}
