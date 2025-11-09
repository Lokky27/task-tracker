package ru.srfholding.userdetails.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.srfholding.userdetails.converter.TeamMemberRoleConverter;
import ru.srfholding.userdetails.constant.TeamMemberRoles;

import java.time.OffsetDateTime;

import static ru.srfholding.common.constants.SchemaNames.USER_DETAILS_SERVICE_SCHEMA;


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

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne(fetch = FetchType.LAZY)
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
