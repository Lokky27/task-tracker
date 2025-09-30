package ru.holding.srf.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.holding.srf.userservice.service.UserService;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.users.request.CreateUserRequest;
import ru.srfholding.trackerdto.users.request.UpdateUserRequest;
import ru.srfholding.trackerdto.users.response.UserResult;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    @Transactional
    public TrackerResponse<UserResult> getUserById(String rqUid, String rqTm, UUID userId) {
        return null;
    }

    @Override
    @Transactional
    public TrackerResponse<UserResult> getUserByEmail(String rqUid, String rqTm, String email) {

        return null;
    }

    @Override
    @Transactional
    public TrackerResponse<UserResult> createUser(String rqUid, String rqTm, CreateUserRequest request) {
        return null;
    }

    @Override
    @Transactional
    public TrackerResponse<UserResult> updateUser(String rqUid, String rqTm, UUID userId, UpdateUserRequest request) {
        return null;
    }

    @Override
    @Transactional
    public TrackerResponse<Void> deleteUser(String rqUid, String rqTm, UUID userId) {
        return null;
    }
}
