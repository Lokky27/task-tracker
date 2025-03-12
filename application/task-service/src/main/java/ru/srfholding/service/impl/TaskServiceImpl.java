package ru.srfholding.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.srfholding.mapper.TaskMapper;
import ru.srfholding.repository.TaskRepository;
import ru.srfholding.service.TaskService;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.GetTaskListResponseDto;
import ru.srfholding.trackerdto.task.response.GetTaskResponseDto;
import ru.srfholding.trackermodels.task.TaskEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public GetTaskResponseDto createTask(CreateTaskRequestDto createTaskRequestDto) {
        TaskEntity taskEntity = taskMapper.mapCreateTaskRequestToEntity(createTaskRequestDto);
        taskEntity.setStatusCode(1);
        taskEntity.setCreatedAt(OffsetDateTime.now());

        try {
            TaskEntity savedTask = taskRepository.save(taskEntity);
            return taskMapper.mapTaskEntityToResponse(savedTask);

        } catch (Exception e) {
            log.error("При сохранении задачи возникла ошибка: {}", e.getMessage(), e);
        }

        return null;
    }

    @Override
    public GetTaskResponseDto getTaskById(UUID taskId) {
        return null;
    }

    @Override
    public GetTaskListResponseDto getTasks() {
        return null;
    }
}
