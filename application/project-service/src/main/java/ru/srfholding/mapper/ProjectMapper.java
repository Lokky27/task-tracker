package ru.srfholding.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.srfholding.trackerdto.project.response.ProjectResult;
import ru.srfholding.trackermodels.model.ProjectEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProjectMapper {

    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "projectName", source = "projectName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "projectStatus", source = "statusCode")
    @Mapping(target = "ownerId", source = "owner.userId")
    @Mapping(target = "createdAt", source = "createdAt")
    ProjectResult mapEntityToResult(ProjectEntity entity);
}
