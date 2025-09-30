package ru.srfholding.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.srfholding.service.TaskService;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.task.AddSubtaskRequest;
import ru.srfholding.trackerdto.task.AssignTaskRequest;
import ru.srfholding.trackerdto.task.ChangeStatusTaskRequest;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.AssignTaskBody;
import ru.srfholding.trackerdto.task.response.ChangeStatusTaskBody;
import ru.srfholding.trackerdto.task.response.TaskResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Override
    @Transactional
    public TrackerResponse<TaskResult> createTask(String rqUid, String rqTm, CreateTaskRequestDto createTaskRequestDto) {

        return null;
    }

    @Override
    public TrackerResponse<TaskResult> getTaskById(String rqUid, String rqTm, UUID taskId) {

        return null;
    }

    @Override
    public List<TrackerResponse<TaskResult>> getTasks(String rqUid, String rqTm) {
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public TrackerResponse<ChangeStatusTaskBody> changeTaskStatus(String rqUid, String rqTm, UUID taskId, ChangeStatusTaskRequest changeStatusTaskRequest) {

        return null;
    }

    @Override
    @Transactional
    public TrackerResponse<AssignTaskBody> assignTask(String rqUid, String rqTm, UUID taskId, AssignTaskRequest request) {

        return null;
    }

    @Override
    @Transactional
    public TrackerResponse<TaskResult> addSubTask(String rqUid, String rqTm, UUID parentTaskId, AddSubtaskRequest request) {
        return null;
    }
}
