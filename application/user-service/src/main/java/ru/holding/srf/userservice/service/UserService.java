package ru.holding.srf.userservice.service;

import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.users.request.CreateUserRequest;
import ru.srfholding.trackerdto.users.response.UserResult;

import java.util.UUID;

/**
 * Сервис работы с пользователями (для роли ADMIN)
 */
public interface UserService {

    /**
     * Получение пользователя по ID
     * @param rqUid - UUID Запроса
     * @param rqTm - Временная метка запроса
     * @param userId - ID пользователя
     * @return - результат
     */
    TrackerResponse<UserResult> getUserById(String rqUid, String rqTm, UUID userId);

    /**
     * Добавление пользователя
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @param request - Запрос на сохранение пользователя
     * @return - результат
     */
    TrackerResponse<UserResult> createUser(String rqUid, String rqTm, CreateUserRequest request);
}
