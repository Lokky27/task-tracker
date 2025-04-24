package ru.srfholding.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.srfholding.service.ProjectService;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.project.request.CreateProjectRequest;
import ru.srfholding.trackerdto.project.response.ProjectResult;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<TrackerResponse<ProjectResult>> createProject(
            @RequestHeader("rqUid") String rqUid,
            @RequestHeader("rqTm") String rqTm,
            @Valid @RequestBody CreateProjectRequest request) {
        TrackerResponse<ProjectResult> project = projectService.createProject(rqUid, rqTm, request);

        return ResponseEntity.ok(project);
    }
}
