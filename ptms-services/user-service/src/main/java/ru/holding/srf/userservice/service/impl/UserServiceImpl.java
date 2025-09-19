package ru.holding.srf.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.holding.srf.userservice.exception.UserNotFoundException;
import ru.holding.srf.userservice.service.UserService;
import ru.srfholding.trackerdto.base.ResponseBuilder;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.mapper.UserMapper;
import ru.srfholding.trackerdto.users.request.CreateUserRequest;
import ru.srfholding.trackerdto.users.request.UpdateUserRequest;
import ru.srfholding.trackerdto.users.response.UserResult;
import ru.srfholding.trackermodels.model.UserEntity;
import ru.srfholding.trackermodels.repository.UserRepository;

import java.util.UUID;

import static java.lang.String.format;
import static ru.srfholding.trackermodels.converter.constant.UserStatus.ACTIVE;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TrackerResponse<UserResult> getUserById(String rqUid, String rqTm, UUID userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            log.warn("Пользователь с ID [{}] не найден в системе", userId);
            throw new UserNotFoundException(format("Пользователь с ID [%s] в системе не найден", userId));
        }
        UserResult userResult = userMapper.mapResult(userEntity);

        return ResponseBuilder.<UserResult> success(rqUid, rqTm)
                .withResult(userResult)
                .build();
    }

    @Override
    @Transactional
    public TrackerResponse<UserResult> getUserByEmail(String rqUid, String rqTm, String email) {
        UserEntity userEntity = userRepository.findAllByEmail(email);
        if (userEntity == null) {
            log.warn("Пользователь по email [{}] не найден", email);
            throw new UserNotFoundException(format("Пользователь по email: [%s] не найден", email));
        }
        UserResult userResult = userMapper.mapResult(userEntity);

        return ResponseBuilder.<UserResult>success(rqUid, rqTm)
                .withResult(userResult)
                .build();
    }

    @Override
    @Transactional
    public TrackerResponse<UserResult> createUser(String rqUid, String rqTm, CreateUserRequest request) {
        UserEntity userEntity = userMapper.mapToUserEntity(request);
        userEntity.setStatus(ACTIVE);
        UserEntity savedUser = userRepository.save(userEntity);

        return ResponseBuilder.<UserResult> success(rqUid, rqTm)
                .withResult(userMapper.mapResult(savedUser))
                .build();
    }

    @Override
    @Transactional
    public TrackerResponse<UserResult> updateUser(String rqUid, String rqTm, UUID userId, UpdateUserRequest request) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            log.warn("Пользователь с ID: [{}] в системе не найден", userId);
            throw new UserNotFoundException(format("Пользователь с ID [%s] в системе не найден", userId));
        }
        userEntity.setDisplayName(request.getLogin());
        userEntity.setAvatarUri(request.getAvatarUrl());
        userEntity.setTimezone(request.getTimezone());
        userEntity.setLanguage(request.getLanguage());
        userEntity.setStatus(request.getStatus());
        UserResult userResult = userMapper.mapResult(userEntity);

        return ResponseBuilder.<UserResult>success(rqUid, rqTm)
                .withResult(userResult)
                .build();
    }

    @Override
    @Transactional
    public TrackerResponse<Void> deleteUser(String rqUid, String rqTm, UUID userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity == null) {
            log.warn("Пользователь с ID: [{}] в системе не найден", userId);
            throw new UserNotFoundException(format("Пользователь с ID [%s] в системе не найден", userId));
        }
        userRepository.delete(userEntity);

        return ResponseBuilder.<Void>success(rqUid, rqTm)
                .build();
    }
}
