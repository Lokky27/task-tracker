package ru.srfholding.service;

import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.project.request.CreateProjectRequest;
import ru.srfholding.trackerdto.project.response.ProjectResult;

/**
 * Сервис управления проектами
 */
public interface ProjectService {

    /**
     * Создание нового проекта
     * @param request - запрос на создание проекта
     * @return - ответ о создании проекта
     */
    TrackerResponse<ProjectResult> createProject(String rqUid, String rqTm, CreateProjectRequest request);
}
