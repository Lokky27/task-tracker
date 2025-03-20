package ru.srfholding.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.srfholding.mapper.TaskMapper;
import ru.srfholding.service.TaskService;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.GetTaskListResponseDto;
import ru.srfholding.trackerdto.task.response.GetTaskResponseDto;
import ru.srfholding.trackerdto.task.response.ResponseError;
import ru.srfholding.trackermodels.model.ProjectEntity;
import ru.srfholding.trackermodels.model.TaskEntity;
import ru.srfholding.trackermodels.model.UserEntity;
import ru.srfholding.trackermodels.repository.ProjectRepository;
import ru.srfholding.trackermodels.repository.TaskRepository;
import ru.srfholding.trackermodels.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GetTaskResponseDto createTask(CreateTaskRequestDto createTaskRequestDto) {
        GetTaskResponseDto getTaskResponseDto;
        TaskEntity taskEntity = taskMapper.mapCreateTaskRequestToEntity(createTaskRequestDto);
        taskEntity.setStatusCode(1);

        ProjectEntity project = projectRepository.findById(createTaskRequestDto.getProjectId())
                .orElse(null);
        if (project != null) {
            taskEntity.setProject(project);
        } else {
            log.error("Проект с id {} не найден", createTaskRequestDto.getProjectId());
            getTaskResponseDto = new GetTaskResponseDto();
            getTaskResponseDto.setSuccess(false);
            getTaskResponseDto.setStatusCode(3);
            getTaskResponseDto.setErrors(getErrors(
                    "123",
                    String.format("Проект с id %s не найден", createTaskRequestDto.getProjectId())
            ));
        }

        UserEntity userById = userRepository.findById(createTaskRequestDto.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id " + createTaskRequestDto.getAssigneeId() + " не найден"));
        taskEntity.setAssignee(userById);
        taskEntity.setCreatedBy(userById);
        taskEntity.setUpdatedBy(userById);

        try {
            TaskEntity savedTask = taskRepository.save(taskEntity);
            getTaskResponseDto = taskMapper.mapTaskEntityToResponse(savedTask);

        } catch (Exception e) {
            log.error("При сохранении задачи возникла ошибка: {}", e.getMessage(), e);
            getTaskResponseDto = new GetTaskResponseDto();
            getTaskResponseDto.setSuccess(false);
            getTaskResponseDto.setResult(null);
            getTaskResponseDto.setStatusCode(3);
            getTaskResponseDto.setErrors(getErrors("120", e.getMessage()));
        }

        return getTaskResponseDto;
    }

    @Override
    public GetTaskResponseDto getTaskById(UUID taskId) {
        GetTaskResponseDto getTaskResponseDto = null;
        try {
            TaskEntity taskEntity = taskRepository.findById(taskId).orElse(null);
            if (taskEntity != null) {
                getTaskResponseDto = taskMapper.mapTaskEntityToResponse(taskEntity);
                getTaskResponseDto.setStatusCode(1);
            }
        } catch (Exception e) {
            log.error("При получении задачи возникла ошибка: {}", e.getMessage(), e);
            getTaskResponseDto = new GetTaskResponseDto();
            getTaskResponseDto.setSuccess(false);
            getTaskResponseDto.setResult(null);
            getTaskResponseDto.setStatusCode(3);
            getTaskResponseDto.setErrors(getErrors("120", e.getMessage()));
        }
        return getTaskResponseDto;
    }

    @Override
    public GetTaskListResponseDto getTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();

        return GetTaskListResponseDto.builder()
                .tasks(taskMapper.mapTaskEntityListToResponseList(taskEntities))
                .build();
    }

    private List<ResponseError> getErrors(String errorCode, String errorMsg) {
        List<ResponseError> errors = new ArrayList<>();
        errors.add(ResponseError.builder()
                        .errorCode(errorCode)
                        .errorDescription(errorMsg)
                .build());
        return errors;
    }
}
