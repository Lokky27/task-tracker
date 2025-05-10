package ru.holding.srf.userservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.holding.srf.userservice.exception.UserNotFoundException;
import ru.holding.srf.userservice.rest.UserController;
import ru.srfholding.trackerdto.base.ResponseBuilder;
import ru.srfholding.trackerdto.base.response.TrackerErrors;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.base.response.ValidationErrorDto;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static ru.srfholding.trackerdto.users.constant.UserServiceError.FIELD_VALUES_UNEXPECTED;
import static ru.srfholding.trackerdto.users.constant.UserServiceError.USER_PROFILE_NOT_FOUND;

@Slf4j
@RestControllerAdvice(basePackageClasses = UserController.class)
public class GlobalUserControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String rqUid = getHeaderFromRequest(request, "rqUid");
        String rqTm = getHeaderFromRequest(request, "rqTm");
        List<ValidationErrorDto> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ValidationErrorDto.builder()
                        .fieldName(fieldError.getField())
                        .errorMessage(Optional.of(fieldError.getDefaultMessage()).orElse("Некорректное значение"))
                        .build())
                .toList();
        log.warn("Ошибка валидации запроса rqUid:[{}], rqTm: [{}]", rqUid, rqTm, ex);
        TrackerResponse<Object> error = ResponseBuilder.<Object>error(rqUid, rqTm)
                .withError(FIELD_VALUES_UNEXPECTED)
                .withData(validationErrors)
                .build();

        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<TrackerResponse<Void>> handleUserNotFoundException(UserNotFoundException e,
                                                                             WebRequest request) {
        String rqUid = getHeaderFromRequest(request, "rqUid");
        String rqTm = getHeaderFromRequest(request, "rqTm");
        log.warn("Пользователь не найден: rqUid [{}], rqTm: [{}]", rqUid, rqTm, e);
        TrackerResponse<Void> error = buildErrorResponse(rqUid, rqTm, USER_PROFILE_NOT_FOUND);

        return new ResponseEntity<>(error, NOT_FOUND);
    }

    private String getHeaderFromRequest(WebRequest request, String headerName) {
        if (request instanceof ServletWebRequest servletWebRequest) {
            return servletWebRequest.getRequest().getHeader(headerName);
        }

        return null;
    }

    private TrackerResponse<Void> buildErrorResponse(String rqUid, String rqTm, TrackerErrors errors) {
        return ResponseBuilder.<Void>error(rqUid, rqTm)
                .withError(errors)
                .build();
    }
}
