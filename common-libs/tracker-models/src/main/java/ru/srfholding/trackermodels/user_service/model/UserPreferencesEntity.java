package ru.srfholding.trackermodels.user_service.model;

import jakarta.persistence.*;
import lombok.*;
import ru.srfholding.trackermodels.user_service.converter.UserPreferencesThemeConverter;
import ru.srfholding.trackermodels.user_service.converter.UserPreferencesTimeFormatConverter;
import ru.srfholding.trackermodels.user_service.constant.UserPreferencesThemes;
import ru.srfholding.trackermodels.user_service.constant.UserPreferencesTimeFormats;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.NONE;
import static ru.srfholding.trackermodels.common_constants.SchemaNames.USER_DETAILS_SERVICE_SCHEMA;

/**
 * Пользовательские настройки профиля
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_preferences", schema = USER_DETAILS_SERVICE_SCHEMA)
public class UserPreferencesEntity {
    /**
     * ID настройки
     */
    @Id
    @Column(name = "user_profile_id", nullable = false, updatable = false)
    @Setter(NONE)
    private UUID userProfileId;

    @OneToOne(fetch = LAZY)
    @MapsId
    @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id")
    private UserProfileEntity userProfile;
    /**
     * Настройка темы
     */
    @Column(name = "theme")
    @Convert(converter = UserPreferencesThemeConverter.class)
    private UserPreferencesThemes theme;
    /**
     * Настройка формата даты
     */
    @Column(name = "date_format", length = 20)
    private String dateFormat;
    /**
     * Тип формата временеи (24ч/12ч)
     */
    @Column(name = "type_format")
    @Convert(converter = UserPreferencesTimeFormatConverter.class)
    private UserPreferencesTimeFormats timeFormats;
}
