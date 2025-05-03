package ru.srfholding.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.srfholding.exception.AssigneeNotFoundException;
import ru.srfholding.exception.InvalidChangeStatusException;
import ru.srfholding.exception.ProjectNotFoundException;
import ru.srfholding.exception.TaskNotFoundException;
import ru.srfholding.trackerdto.mapper.TaskMapper;
import ru.srfholding.service.TaskService;
import ru.srfholding.trackerdto.base.ResponseBuilder;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.task.AddSubtaskRequest;
import ru.srfholding.trackerdto.task.AssignTaskRequest;
import ru.srfholding.trackerdto.task.ChangeStatusTaskRequest;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.AssignTaskBody;
import ru.srfholding.trackerdto.task.response.ChangeStatusTaskBody;
import ru.srfholding.trackerdto.task.response.TaskResult;
import ru.srfholding.trackermodels.converter.constant.StatusType;
import ru.srfholding.trackermodels.model.ProjectEntity;
import ru.srfholding.trackermodels.model.TaskEntity;
import ru.srfholding.trackermodels.model.UserEntity;
import ru.srfholding.trackermodels.repository.ProjectRepository;
import ru.srfholding.trackermodels.repository.TaskRepository;
import ru.srfholding.trackermodels.repository.UserRepository;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
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
    public TrackerResponse<TaskResult> createTask(String rqUid, String rqTm, CreateTaskRequestDto createTaskRequestDto) {
        TaskEntity taskEntity = taskMapper.mapCreateTaskRequestToEntity(createTaskRequestDto);
        taskEntity.setStatusCode(NEW);

        ProjectEntity project = projectRepository.findById(createTaskRequestDto.getProjectId())
                .orElse(null);
        if (project == null) {
            log.error("Проект с id [{}] не найден", createTaskRequestDto.getProjectId());
            throw new ProjectNotFoundException(format("Проект с id [%s] не найден", createTaskRequestDto.getProjectId()));
        }
        taskEntity.setProject(project);

        UserEntity assignee = userRepository.findById(createTaskRequestDto.getAssigneeId())
                .orElse(null);
        if (assignee == null) {
            log.error("Пользователь с ID: [{}] не найден в системе", createTaskRequestDto.getAssigneeId());
            throw new AssigneeNotFoundException(format("Пользователь с ID: [%s] не найден в системе", createTaskRequestDto.getAssigneeId()));
        }
        //TODO: Добавить проверку участия исполнителя в проекте (Реализовать ProjectMembers)
        taskEntity.setAssignee(assignee);
        taskEntity.setCreatedBy(assignee);
        taskEntity.setUpdatedBy(assignee);

        TaskEntity savedTask = taskRepository.save(taskEntity);
        TaskResult taskResult = taskMapper.mapResult(savedTask);

        return ResponseBuilder.<TaskResult>success(rqUid, rqTm)
                .withResult(taskResult)
                .build();
    }

    @Override
    public TrackerResponse<TaskResult> getTaskById(String rqUid, String rqTm, UUID taskId) {
        TaskEntity taskEntity = taskRepository.findById(taskId).orElse(null);
        if (taskEntity == null) {
            log.error("Задача по ID [{}] не найдена", taskId);
            throw new TaskNotFoundException(format("Задача по ID [%s] не найдена", taskId));
        }
        TaskResult taskResult = taskMapper.mapResult(taskEntity);

        return ResponseBuilder.<TaskResult>success(rqUid, rqTm)
                .withResult(taskResult)
                .build();
    }

    @Override
    public List<TrackerResponse<TaskResult>> getTasks(String rqUid, String rqTm) {
        return taskRepository.findAll().stream()
                .map(taskMapper::mapResult)
                .map(result -> ResponseBuilder.<TaskResult>success(rqUid, rqTm)
                        .withResult(result)
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public TrackerResponse<ChangeStatusTaskBody> changeTaskStatus(String rqUid, String rqTm, UUID taskId, ChangeStatusTaskRequest changeStatusTaskRequest) {
        TaskEntity task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            log.warn("Задача с ID [{}] в системе не найдена", taskId);
            throw new TaskNotFoundException(String.format("Задача с ID [%s] в системе не найдена", taskId));
        }
        StatusType newStatus = changeStatusTaskRequest.getStatusCode();
        StatusType oldStatus = task.getStatusCode();
        List<Integer> nextStatusCodes = oldStatus.getNextStatusCodes();

        if (!nextStatusCodes.contains(newStatus.getCode())) {
            log.warn("Задача [{}] не может быть переведна в статус [{}]", taskId, newStatus.getDescription());
            throw new InvalidChangeStatusException(String.format("Задача [%s] не может быть переведна в статус [%s]", taskId, newStatus.getDescription()));
        }

        task.setStatusCode(newStatus);
        TaskEntity updatedTask = taskRepository.save(task);


        return ResponseBuilder.<ChangeStatusTaskBody>success(rqUid, rqTm)
                .withResult(ChangeStatusTaskBody.builder()
                        .taskID(updatedTask.getTaskId())
                        .oldStatus(oldStatus)
                        .newStatus(updatedTask.getStatusCode())
                        .build())
                .build();
    }

    @Override
    @Transactional
    public TrackerResponse<AssignTaskBody> assignTask(String rqUid, String rqTm, UUID taskId, AssignTaskRequest request) {
        UUID newAssigneeId = request.getAssigneeId();
        UserEntity assignee = userRepository.findById(newAssigneeId).orElse(null);
        if (assignee == null) {
            log.warn("Исполнитель с UUID: [{}] в системе не найден", newAssigneeId);
            throw new AssigneeNotFoundException(String.format("Исполнитель с UUID: [%s] в системе не найден", newAssigneeId));
        }
        //TODO: Добавить проверку участия исполнителя в проекте (Реализовать ProjectMembers)
        TaskEntity task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            log.warn("Задача с UUID: [{}] в системе не найдена", taskId);
            throw new TaskNotFoundException(format("Задача с UUID [%s] в системе не найдне", taskId));
        }

        task.setAssignee(assignee);
        TaskEntity updatedTask = taskRepository.save(task);

        return ResponseBuilder.<AssignTaskBody>success(rqUid, rqTm)
                .withResult(AssignTaskBody.builder()
                        .taskId(updatedTask.getTaskId())
                        .oldAssignee(task.getAssignee().getUserId())
                        .assigneeId(assignee.getUserId())
                        .build())
                .build();
    }

    @Override
    @Transactional
    public TrackerResponse<TaskResult> addSubTask(String rqUid, String rqTm, UUID parentTaskId, AddSubtaskRequest request) {
        return null;
    }
}
