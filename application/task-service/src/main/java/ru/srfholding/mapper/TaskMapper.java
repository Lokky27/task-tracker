package ru.srfholding.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.GetTaskResponseDto;
import ru.srfholding.trackerdto.task.response.ResponseError;
import ru.srfholding.trackerdto.task.response.Result;
import ru.srfholding.trackermodels.model.TaskEntity;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Mapping(target = "title", source = "title")
    @Mapping(target = "taskTypeCode", source = "type")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "deadline", source = "deadline")
    TaskEntity mapCreateTaskRequestToEntity(CreateTaskRequestDto requestDto);

    @Mapping(target = "result", source = "taskEntity")
    @Mapping(target = "success", constant = "true")
    GetTaskResponseDto mapTaskEntityToResponse(TaskEntity taskEntity);

    List<GetTaskResponseDto> mapTaskEntityListToResponseList(List<TaskEntity> taskEntityList);

    @Mapping(target = "taskId", source = "taskId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "statusCode", source = "statusCode")
    @Mapping(target = "taskTypeCode", source = "taskTypeCode")
    @Mapping(target = "projectId", source = "taskEntity.project.projectId")
    @Mapping(target = "assigneeId", source = "taskEntity.assignee.userId")
    @Mapping(target = "reporterId", source = "taskEntity.createdBy.userId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    Result mapResult(TaskEntity taskEntity);

    ResponseError mapResponseError(String errorCode, String errorMsg);
}
