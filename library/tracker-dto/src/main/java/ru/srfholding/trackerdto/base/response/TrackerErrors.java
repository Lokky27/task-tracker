package ru.srfholding.trackerdto.base.response;


import java.time.Instant;

public interface TrackerErrors {

    String getCode();

    String getDescription();

    ErrorsType getType();

    default ResponseError toResponseError() {
        return ResponseError.builder()
                .errorCode(getCode())
                .errorMessage(getDescription())
                .errorType(getType())
                .timestamp(Instant.now())
                .build();
    }
}
