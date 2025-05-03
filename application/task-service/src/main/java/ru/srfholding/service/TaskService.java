package ru.srfholding.service;

import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.task.AddSubtaskRequest;
import ru.srfholding.trackerdto.task.AssignTaskRequest;
import ru.srfholding.trackerdto.task.ChangeStatusTaskRequest;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.AssignTaskBody;
import ru.srfholding.trackerdto.task.response.ChangeStatusTaskBody;
import ru.srfholding.trackerdto.task.response.TaskResult;

import java.util.List;
import java.util.UUID;

/**
 * Сервис работы с задачами
 */
public interface TaskService {

    /**
     * Создание задачи
     * @param createTaskRequestDto - запрос на создание задачи
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @return - задача
     */
    TrackerResponse<TaskResult> createTask(String rqUid, String rqTm, CreateTaskRequestDto createTaskRequestDto);

    /**
     * Получение задачи по UUID
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @param taskId - UUID задачи
     * @return - задача
     */
    TrackerResponse<TaskResult> getTaskById(String rqUid, String rqTm, UUID taskId);

    /**
     * Получение списка задач
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @return - список задач
     */
    List<TrackerResponse<TaskResult>> getTasks(String rqUid, String rqTm);

    /**
     * Смена статуса задачи
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @param taskId - ID задачи
     * @param changeStatusTaskRequest - запрос смены статуса
     * @return ответ смены статуса
     */
    TrackerResponse<ChangeStatusTaskBody> changeTaskStatus(String rqUid, String rqTm, UUID taskId, ChangeStatusTaskRequest changeStatusTaskRequest);


    /**
     * Назначение задачи на исполнителя
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @param taskId - UUID задачи
     * @param request - запрос на назначение задачи
     * @return - обновленная задача
     */
    TrackerResponse<AssignTaskBody> assignTask(String rqUid, String rqTm, UUID taskId, AssignTaskRequest request);

    /**
     * Добавление подзадачи
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @param parentTaskId - UUID родительской задачи
     * @param request - Запрос на добавление подзадачи
     */
    TrackerResponse<TaskResult> addSubTask(String rqUid, String rqTm, UUID parentTaskId, AddSubtaskRequest request);
}
