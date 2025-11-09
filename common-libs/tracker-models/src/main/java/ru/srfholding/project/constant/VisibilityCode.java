package ru.srfholding.project.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static java.lang.String.format;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum VisibilityCode {

    OPEN(1, "Открыт"),
    CLOSE(2, "Закрыт");

    private final Integer code;
    private final String description;

    public static VisibilityCode getByCode(Integer code) {
        if (code == null) {
            log.warn("Код сущности [{}] пустой!", VisibilityCode.class);
            throw new IllegalArgumentException(format("Код сущности [%s] пустой", VisibilityCode.class));
        }

        return Arrays.stream(values())
                .filter(visibilityCode -> code.equals(visibilityCode.getCode()))
                .findAny()
                .orElse(null);
    }
}
