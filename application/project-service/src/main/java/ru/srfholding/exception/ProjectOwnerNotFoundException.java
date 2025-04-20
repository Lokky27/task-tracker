package ru.srfholding.exception;

import lombok.Getter;
import ru.srfholding.trackerdto.base.response.TrackerErrors;

@Getter
public class ProjectOwnerNotFoundException extends RuntimeException {
    private final TrackerErrors error;

    public ProjectOwnerNotFoundException(TrackerErrors error) {
        super(error.getDescription());
        this.error = error;
    }
}
