package ru.srfholding.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.srfholding.mapper.ProjectMapper;
import ru.srfholding.exception.ProjectOwnerNotFoundException;
import ru.srfholding.service.ProjectService;
import ru.srfholding.trackerdto.base.ResponseBuilder;
import ru.srfholding.trackerdto.base.response.TrackerErrors;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.project.request.CreateProjectRequest;
import ru.srfholding.trackerdto.project.response.ProjectResult;
import ru.srfholding.trackermodels.model.ProjectEntity;
import ru.srfholding.trackermodels.model.UserEntity;
import ru.srfholding.trackermodels.repository.ProjectRepository;
import ru.srfholding.trackermodels.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.srfholding.trackerdto.project.constant.ProjectError.PROJECT_NAME_IS_EMPTY;
import static ru.srfholding.trackerdto.project.constant.ProjectError.PROJECT_OWNER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public TrackerResponse<ProjectResult> createProject(String rqUid, String rqTm, CreateProjectRequest request) {
        List<TrackerErrors> errors = new ArrayList<>();

        if (StringUtils.isBlank(request.getName())) {
            errors.add(PROJECT_NAME_IS_EMPTY);
        }

        if (!errors.isEmpty()) {
            log.warn("Ошибка валидации: {}", errors);
            return ResponseBuilder.<ProjectResult>error(rqUid, rqTm)
                    .withErrors(errors)
                    .build();
        }

        UserEntity owner = userRepository.findById(request.getOwnerId())
                .orElse(null);
        if (owner == null) {
            log.warn("Владелец с ID {} для проекта {} не найден в системе", request.getOwnerId(), request.getName());
            throw new ProjectOwnerNotFoundException(PROJECT_OWNER_NOT_FOUND);
        }

        ProjectEntity project = new ProjectEntity();
        project.setProjectName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwner(owner);

        ProjectEntity savedProject = projectRepository.save(project);
        ProjectResult projectResult = projectMapper.mapEntityToResult(savedProject);

        log.info("Проект [{}] успешно создан владельцем [{}]", savedProject.getProjectName(), owner.getUserId());

        return ResponseBuilder.<ProjectResult> success(rqUid, rqTm)
                .withResult(projectResult)
                .build();
    }
}
