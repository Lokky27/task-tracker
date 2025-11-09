package ru.srfholding.userdetails.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.common.exception.TeamMemberRoleNotFoundException;

import java.util.Arrays;

/**
 * Роли членов команды
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public enum TeamMemberRoles {
    MEMBER(1, "Член команды"),
    LEAD(2, "Лидер команды");

    private final Integer code;
    private final String description;

    public static Integer getFromCode(TeamMemberRoles type) {
        return type.getCode();
    }

    public static TeamMemberRoles getCodeFromType(Integer code) {
        return Arrays.stream(TeamMemberRoles.values())
                .filter(teamMemberRole -> code.equals(teamMemberRole.code))
                .findAny()
                .orElseThrow(() -> {
                    log.warn("Роль по коду {} не найдена!", code);
                    return new TeamMemberRoleNotFoundException(String.format("Роль по коду %d не найдена", code));
                });
    }
}
