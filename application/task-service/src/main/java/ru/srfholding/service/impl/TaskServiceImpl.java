package ru.srfholding.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.srfholding.mapper.TaskMapper;
import ru.srfholding.service.TaskService;
import ru.srfholding.trackerdto.base.response.ResponseError;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.task.ChangeStatusTaskRequest;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.ChangeStatusTaskBody;
import ru.srfholding.trackerdto.task.response.GetTaskResponseDto;
import ru.srfholding.trackerdto.task.response.TaskResult;
import ru.srfholding.trackermodels.model.ProjectEntity;
import ru.srfholding.trackermodels.model.TaskEntity;
import ru.srfholding.trackermodels.model.UserEntity;
import ru.srfholding.trackermodels.repository.ProjectRepository;
import ru.srfholding.trackermodels.repository.TaskRepository;
import ru.srfholding.trackermodels.repository.UserRepository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static ru.srfholding.trackerdto.base.response.ErrorsType.BUSINESS;
import static ru.srfholding.trackerdto.base.response.ErrorsType.TECHNICAL;
import static ru.srfholding.trackerdto.base.response.Status.ERROR;
import static ru.srfholding.trackerdto.base.response.Status.SUCCESS;
import static ru.srfholding.trackermodels.converter.constant.StatusType.NEW;

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
    public TrackerResponse<TaskResult> createTask(CreateTaskRequestDto createTaskRequestDto) {
        GetTaskResponseDto getTaskResponseDto;
        TaskEntity taskEntity = taskMapper.mapCreateTaskRequestToEntity(createTaskRequestDto);
        taskEntity.setStatusCode(NEW);

        ProjectEntity project = projectRepository.findById(createTaskRequestDto.getProjectId())
                .orElse(null);
        if (project != null) {
            taskEntity.setProject(project);
        } else {
            log.error("Проект с id {} не найден", createTaskRequestDto.getProjectId());
            getTaskResponseDto = new GetTaskResponseDto();
            getTaskResponseDto.setStatus(SUCCESS);
            getTaskResponseDto.getErrors().add(ResponseError.builder()
                            .errorCode("123")
                            .errorMessage(String.format("Проект с id %s не найден", createTaskRequestDto.getProjectId()))
                            .errorType(BUSINESS)
                            .timestamp(Instant.now())
                    .build());
        }

        UserEntity userById = userRepository.findById(createTaskRequestDto.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id " + createTaskRequestDto.getAssigneeId() + " не найден"));
        taskEntity.setAssignee(userById);
        taskEntity.setCreatedBy(userById);
        taskEntity.setUpdatedBy(userById);

        try {
            TaskEntity savedTask = taskRepository.save(taskEntity);
            TaskResult taskResult = taskMapper.mapResult(savedTask);

        } catch (Exception e) {
            log.error("При сохранении задачи возникла ошибка: {}", e.getMessage(), e);
            getTaskResponseDto = new GetTaskResponseDto();
            getTaskResponseDto.setStatus(ERROR);
            getTaskResponseDto.setTaskResult(null);
            getTaskResponseDto.getErrors().add(ResponseError.builder()
                            .errorCode("120")
                            .errorMessage(e.getMessage())
                            .errorType(TECHNICAL)
                            .timestamp(Instant.now())
                    .build());
        }

        return null;
    }

    @Override
    public TrackerResponse<TaskResult> getTaskById(UUID taskId) {
        GetTaskResponseDto getTaskResponseDto;
        try {
            TaskEntity taskEntity = taskRepository.findById(taskId).orElse(null);
            if (taskEntity != null) {
//                getTaskResponseDto = taskMapper.mapTaskEntityToResponse(taskEntity);
//                getTaskResponseDto.setStatus(SUCCESS);
            }
        } catch (Exception e) {
//            log.error("При получении задачи возникла ошибка: {}", e.getMessage(), e);
//            getTaskResponseDto = new GetTaskResponseDto();
//            getTaskResponseDto.setStatus(ERROR);
//            getTaskResponseDto.setTaskResult(null);
//            getTaskResponseDto.getErrors().add(ResponseError.builder()
//                            .errorCode("120")
//                            .errorMessage(e.getMessage())
//                            .errorType(TECHNICAL)
//                            .timestamp(Instant.now())
//                    .build());
        }
        return null;
    }

    @Override
    public List<TrackerResponse<TaskResult>> getTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();

        return Collections.emptyList();
    }

    @Override
    @Transactional
    public TrackerResponse<ChangeStatusTaskBody> changeTaskStatus(UUID taskId, ChangeStatusTaskRequest changeStatusTaskRequest) {

        return null;
    }
}
