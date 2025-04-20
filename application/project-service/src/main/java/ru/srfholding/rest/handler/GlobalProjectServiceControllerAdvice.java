package ru.srfholding.rest.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.srfholding.exception.ProjectOwnerNotFoundException;
import ru.srfholding.trackerdto.base.ResponseBuilder;
import ru.srfholding.trackerdto.base.response.TrackerResponse;

import static ru.srfholding.trackerdto.project.constant.ProjectError.CREATE_PROJECT_FIELDS_INVALID;
import static ru.srfholding.trackerdto.project.constant.ProjectError.PROJECT_OWNER_NOT_FOUND;

@Slf4j
@ControllerAdvice
public class GlobalProjectServiceControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TrackerResponse<?>> handleValidationException(MethodArgumentNotValidException e,
                                                                        @RequestHeader("rqUid") String rqUid,
                                                                        @RequestHeader("rqTm") String rqTm) {
        log.warn("Ошибка валидации запроса {}", e.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ResponseBuilder.<Void>error(rqUid, rqTm)
                        .withError(CREATE_PROJECT_FIELDS_INVALID)
                        .build());
    }

    @ExceptionHandler(ProjectOwnerNotFoundException.class)
    public ResponseEntity<TrackerResponse<?>> handleProjectOwnerNotFound(ProjectOwnerNotFoundException e,
                                                                         @RequestHeader("rqUid") String rqUid,
                                                                         @RequestHeader("rqTm") String rqTm) {
        log.warn("Владелец продукта не найден в системе {}", e.getMessage(), e);

        return ResponseEntity
                .badRequest()
                .body(ResponseBuilder.<Void>error(rqUid, rqTm)
                        .withError(PROJECT_OWNER_NOT_FOUND)
                        .build());

    }
}
