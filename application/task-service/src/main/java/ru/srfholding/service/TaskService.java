package ru.srfholding.service;

import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.GetTaskListResponseDto;
import ru.srfholding.trackerdto.task.response.GetTaskResponseDto;

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
    GetTaskResponseDto createTask(CreateTaskRequestDto createTaskRequestDto);

    /**
     * Получение задачи по UUID
     * @param taskId - UUID задачи
     * @return - задача
     */
    GetTaskResponseDto getTaskById(UUID taskId);

    /**
     * Получение списка задач
     * @return - список задач
     */
    GetTaskListResponseDto getTasks();
}
