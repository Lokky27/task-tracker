package ru.holding.srf.userservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.holding.srf.userservice.exception.UserNotFoundException;
import ru.srfholding.trackerdto.base.ResponseBuilder;
import ru.srfholding.trackerdto.base.response.TrackerResponse;

import static ru.srfholding.trackerdto.users.constant.UserServiceError.FIELD_VALUES_UNEXPECTED;
import static ru.srfholding.trackerdto.users.constant.UserServiceError.USER_PROFILE_NOT_FOUND;

@Slf4j
@ControllerAdvice
public class GlobalUserControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TrackerResponse<Void>> handleValidationException(MethodArgumentNotValidException e,
                                                                          @RequestHeader("rqUid") String rqUid,
                                                                          @RequestHeader("rqTm") String rqTm) {
        log.warn("Ошибка валидации запроса rqUid:[{}]", rqUid, e);
        return ResponseEntity.badRequest()
                .body(ResponseBuilder.<Void>error(rqUid, rqTm)
                        .withError(FIELD_VALUES_UNEXPECTED)
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<TrackerResponse<Void>> handleUserNotFoundException(UserNotFoundException e,
                                                                             @RequestHeader("rqUid") String rqUid,
                                                                             @RequestHeader("rqTm") String rqTm) {
        log.error("Ошибка запроса: rqUid [{}]", rqUid, e);

        return ResponseEntity.badRequest()
                .body(ResponseBuilder.<Void>error(rqUid, rqTm)
                        .withError(USER_PROFILE_NOT_FOUND)
                        .build());
    }
}
