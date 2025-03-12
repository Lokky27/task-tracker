package ru.srfholding.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.GetTaskResponseDto;
import ru.srfholding.trackermodels.task.TaskEntity;

@Mapper
public interface TaskMapper {

    @Mapping(target = "title", source = "title")
    @Mapping(target = "taskTypeCode", source = "type")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "assigneeId", source = "assigneeId")
    TaskEntity mapCreateTaskRequestToEntity(CreateTaskRequestDto requestDto);

    @Mapping(target = "taskId", source = "taskId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "statusCode", source = "statusCode")
    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "assigneeId", source = "assigneeId")
    @Mapping(target = "reporterId", source = "createdBy")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "taskTypeCode", source = "taskTypeCode")
    GetTaskResponseDto mapTaskEntityToResponse(TaskEntity taskEntity);
}
