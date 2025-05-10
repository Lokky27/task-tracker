package ru.srfholding.trackerdto.base;

import ru.srfholding.trackerdto.base.response.Status;
import ru.srfholding.trackerdto.base.response.TrackerErrors;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.base.response.ValidationErrorDto;

import java.util.List;

import static ru.srfholding.trackerdto.base.ResponseUtils.buildResponseMetadata;

public class ResponseBuilder<T> {
    private final TrackerResponse<T> response = new TrackerResponse<>();

    public static <T> ResponseBuilder<T> success(String rqUid, String rqTm) {
        ResponseBuilder<T> builder = new ResponseBuilder<>();
        builder.response.setStatus(Status.SUCCESS);
        builder.response.setMetadata(buildResponseMetadata(rqUid, rqTm));

        return builder;
    }

    public static <T> ResponseBuilder<T> error(String rqUid, String rqTm) {
        ResponseBuilder<T> builder = new ResponseBuilder<>();
        builder.response.setStatus(Status.ERROR);
        builder.response.setMetadata(buildResponseMetadata(rqUid, rqTm));
        return builder;
    }

    public ResponseBuilder<T> withResult(T result) {
        response.setResult(result);

        return this;
    }

    public ResponseBuilder<T> withErrors(List<TrackerErrors> errors) {
        response.setErrors(errors.stream()
                .map(TrackerErrors::toResponseError)
                .toList());

        return this;
    }

    public ResponseBuilder<T> withError(TrackerErrors error) {
        response.getErrors().add(error.toResponseError());

        return this;
    }

    public ResponseBuilder<T> withData(List<ValidationErrorDto> data) {
        response.setData(data);

        return this;
    }

    public TrackerResponse<T> build() {
        return response;
    }

}
