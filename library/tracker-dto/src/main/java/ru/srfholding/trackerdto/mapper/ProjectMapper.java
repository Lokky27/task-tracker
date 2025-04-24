package ru.srfholding.trackerdto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.srfholding.trackerdto.project.request.CreateProjectRequest;
import ru.srfholding.trackerdto.project.response.ProjectResult;
import ru.srfholding.trackerdto.task.response.TaskResult;
import ru.srfholding.trackermodels.model.ProjectEntity;
import ru.srfholding.trackermodels.model.TaskEntity;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProjectMapper {

    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "projectName", source = "projectName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "projectStatus", source = "statusCode")
    @Mapping(target = "ownerId", source = "owner.userId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "tasks", source = "tasks", qualifiedByName = "mapTasks")
    ProjectResult mapEntityToResult(ProjectEntity entity);


    @Mapping(target = "projectName", source = "name")
    @Mapping(target = "description", source = "description")
    ProjectEntity mapToEntity(CreateProjectRequest request);

    @Named("mapTasks")
    default List<TaskResult> mapTasks(List<TaskEntity> tasks) {
        TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

        return tasks.stream()
                .map(taskMapper::mapResult)
                .toList();
    }
}
