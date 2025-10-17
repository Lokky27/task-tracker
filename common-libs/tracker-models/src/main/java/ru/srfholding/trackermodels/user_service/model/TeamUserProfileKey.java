package ru.srfholding.trackermodels.user_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Составной ключ для таблицы членов команды
 */
@Embeddable
@Getter
@Setter
public class TeamUserProfileKey implements Serializable {
    /**
     * ID команды
     */
    @Column(name = "team_id")
    private UUID teamId;
    /**
     * ID пользователя
     */
    @Column(name = "user_profile_id")
    private UUID userProfileId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamUserProfileKey that = (TeamUserProfileKey) o;
        return Objects.equals(teamId, that.teamId) && Objects.equals(userProfileId, that.userProfileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, userProfileId);
    }
}
