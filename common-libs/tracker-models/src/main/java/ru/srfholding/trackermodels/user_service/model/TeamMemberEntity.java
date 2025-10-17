package ru.srfholding.trackermodels.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.srfholding.trackermodels.user_service.converter.TeamMemberRoleConverter;
import ru.srfholding.trackermodels.user_service.constant.TeamMemberRoles;

import java.time.OffsetDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static ru.srfholding.trackermodels.common_constants.SchemaNames.USER_DETAILS_SERVICE_SCHEMA;

/**
 * Сущность члена команды
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team_member", schema = USER_DETAILS_SERVICE_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
public class TeamMemberEntity {

    /**
     * Составной первичный ключ
     * team_id
     * user_profile_id
     */
    @EmbeddedId
    private TeamUserProfileKey teamUserProfileKey;

    @ManyToOne(fetch = LAZY)
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne(fetch = LAZY)
    @MapsId("userProfileId")
    @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id")
    private UserProfileEntity userProfile;

    /**
     * Роль в команде
     */
    @Column(name = "member_role")
    @Convert(converter = TeamMemberRoleConverter.class)
    private TeamMemberRoles teamMemberRole;
    /**
     * Дата включения в команду
     */
    @Column(name = "joined_at")
    @CreatedDate
    private OffsetDateTime joinedAt;
    /**
     * Дата исключения из команды
     */
    @Column(name = "left_at")
    private OffsetDateTime leftAt;

}
