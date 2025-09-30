package ru.srfholding.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.srfholding.service.ProjectService;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.project.request.CreateProjectRequest;
import ru.srfholding.trackerdto.project.response.ProjectResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Override
    @Transactional
    public TrackerResponse<ProjectResult> createProject(String rqUid, String rqTm, CreateProjectRequest request) {

        return null;
    }
}
