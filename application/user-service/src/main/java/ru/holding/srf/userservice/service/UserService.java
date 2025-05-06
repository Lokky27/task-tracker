package ru.holding.srf.userservice.service;

import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.users.request.CreateUserRequest;
import ru.srfholding.trackerdto.users.request.UpdateUserRequest;
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
     * Получение пользователя по email
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @param email - email пользователя
     * @return - результат
     */
    TrackerResponse<UserResult> getUserByEmail(String rqUid, String rqTm, String email);

    /**
     * Добавление пользователя
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @param request - Запрос на сохранение пользователя
     * @return - результат
     */
    TrackerResponse<UserResult> createUser(String rqUid, String rqTm, CreateUserRequest request);

    /**
     * Обновление пользователя
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @param userId - ID пользователя
     * @param request - Запрос на обновление
     * @return - результат
     */
    TrackerResponse<UserResult> updateUser(String rqUid, String rqTm, UUID userId, UpdateUserRequest request);

    /**
     * Удаление пользователя
     * @param rqUid - UUID запроса
     * @param rqTm - Временная метка запроса
     * @param userId - ID пользователя
     * @return - результат
     */
    TrackerResponse<Void> deleteUser(String rqUid, String rqTm, UUID userId);
}
