package ru.holding.srf.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.holding.srf.userservice.service.UserService;
import ru.srfholding.trackerdto.base.ResponseBuilder;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.mapper.UserMapper;
import ru.srfholding.trackerdto.users.request.CreateUserRequest;
import ru.srfholding.trackerdto.users.response.UserResult;
import ru.srfholding.trackermodels.model.UserEntity;
import ru.srfholding.trackermodels.repository.UserRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TrackerResponse<UserResult> getUserById(String rqUid, String rqTm, UUID userId) {
        return null;
    }

    @Override
    @Transactional
    public TrackerResponse<UserResult> createUser(String rqUid, String rqTm, CreateUserRequest request) {
        UserEntity userEntity = userMapper.mapToUserEntity(request);

        UserEntity savedUser = userRepository.save(userEntity);
        return ResponseBuilder.<UserResult> success(rqUid, rqTm)
                .withResult(userMapper.mapResult(savedUser))
                .build();
    }
}
