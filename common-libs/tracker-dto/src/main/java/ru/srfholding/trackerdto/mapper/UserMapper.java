package ru.srfholding.trackerdto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.srfholding.trackerdto.users.request.CreateUserRequest;
import ru.srfholding.trackerdto.users.response.UserResult;
import ru.srfholding.trackermodels.model.UserEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "keycloakId", source = "keycloakId")
    @Mapping(target = "displayName", source = "login")
    @Mapping(target = "avatarUri", source = "avatarUrl")
    @Mapping(target = "timezone", source = "timezone")
    @Mapping(target = "language", source = "language")
    UserEntity mapToUserEntity(CreateUserRequest request);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "login", source = "displayName")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "avatarUri", source = "avatarUri")
    @Mapping(target = "timezone", source = "timezone")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "updatedAt", source = "updatedAt")
    UserResult mapResult(UserEntity entity);
}
