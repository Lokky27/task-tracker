package ru.srfholding.service;

import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.task.ChangeStatusTaskRequest;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
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
     * @return - задача
     */
    TrackerResponse<TaskResult> createTask(CreateTaskRequestDto createTaskRequestDto);

    /**
     * Получение задачи по UUID
     * @param taskId - UUID задачи
     * @return - задача
     */
    TrackerResponse<TaskResult> getTaskById(UUID taskId);

    /**
     * Получение списка задач
     * @return - список задач
     */
    List<TrackerResponse<TaskResult>> getTasks();

    /**
     * Смена статуса задачи
     * @param taskId - ID задачи
     * @param changeStatusTaskRequest - запрос смены статуса
     * @return ответ смены статуса
     */
    TrackerResponse<ChangeStatusTaskBody> changeTaskStatus(UUID taskId, ChangeStatusTaskRequest changeStatusTaskRequest);
}
