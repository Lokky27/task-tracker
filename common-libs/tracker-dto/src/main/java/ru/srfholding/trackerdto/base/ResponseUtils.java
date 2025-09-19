package ru.srfholding.trackerdto.base;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.base.response.ResponseMetadata;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import static ru.srfholding.trackerdto.base.response.Status.SUCCESS;

@Slf4j
@UtilityClass
public final class ResponseUtils {

    public static <T> TrackerResponse<T> buildEmptyResponse(String rqUid, String rqTm) {

        return TrackerResponse.<T>builder()
                .metadata(buildResponseMetadata(rqUid, rqTm))
                .status(SUCCESS)
                .build();
    }

    public static ResponseMetadata buildResponseMetadata(String rqUid, String rqTm) {
        OffsetDateTime rqOffsetTime;
        try {
            rqOffsetTime = OffsetDateTime.parse(rqTm);
        } catch (DateTimeParseException e) {
            log.error("rqUid [{}]: Некорректный формат даты rqTm: {}", rqUid, rqTm, e);
            rqOffsetTime = OffsetDateTime.now();
        }

        return ResponseMetadata.builder()
                .rqUid(rqUid)
                .rqTm(rqOffsetTime)
                .build();
    }
}
