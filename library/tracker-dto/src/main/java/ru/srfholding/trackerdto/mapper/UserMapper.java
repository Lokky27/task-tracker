package ru.srfholding.trackerdto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.srfholding.trackerdto.users.request.CreateUserRequest;
import ru.srfholding.trackerdto.users.response.UserResult;
import ru.srfholding.trackermodels.model.UserEntity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "middleName", source = "middleName")
    @Mapping(target = "password", source = "password", qualifiedByName = "mapPassword")
    @Mapping(target = "role", source = "role")
    UserEntity mapToUserEntity(CreateUserRequest request);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "middleName", source = "middleName")
    @Mapping(target = "isActive", source = "isActive")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "role", source = "role")
    UserResult mapResult(UserEntity entity);

    @Named("mapPassword")
    default char[] mapPassword(String password) {
        return password != null ? password.toCharArray() : null;
    }
}
