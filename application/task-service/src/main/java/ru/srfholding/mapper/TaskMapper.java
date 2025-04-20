package ru.srfholding.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.srfholding.trackerdto.task.CreateTaskRequestDto;
import ru.srfholding.trackerdto.task.response.TaskResult;
import ru.srfholding.trackermodels.model.TaskEntity;

@Mapper
public interface TaskMapper {

    @Mapping(target = "title", source = "title")
    @Mapping(target = "taskTypeCode", source = "type")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "deadline", source = "deadline")
    TaskEntity mapCreateTaskRequestToEntity(CreateTaskRequestDto requestDto);

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
    TaskResult mapResult(TaskEntity taskEntity);
}
