package ru.srfholding.project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.common.exception.AttributeCodeIsNullException;

import java.util.stream.Stream;

/**
 * Роли членов в проекте
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public enum ProjectMemberRole {
    MEMBER(1, "MEMBER"),
    LEAD(2, "LEAD");

    private final Integer code;
    private final String description;

    public static ProjectMemberRole getRoleByCode(Integer roleCode) {
        if (roleCode == null) {
            log.warn("Код роли не может быть пустым!");
            throw new AttributeCodeIsNullException("Код роли не может быть пустым!");
        }

        return Stream.of(values())
                .filter(role -> roleCode.equals(role.getCode()))
                .findAny()
                .orElse(null);
    }
}
