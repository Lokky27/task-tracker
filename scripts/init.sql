DO
$$
    BEGIN
        -- Создание схем для каждого микросервиса
        CREATE SCHEMA IF NOT EXISTS user_details_service_schema; -- Для user-service
        CREATE SCHEMA IF NOT EXISTS keycloak_schema; -- Для Keycloak
        GRANT ALL ON SCHEMA keycloak_schema TO postgres;
        CREATE SCHEMA IF NOT EXISTS team_service_schema; -- Для team-service
        CREATE SCHEMA IF NOT EXISTS project_service_schema; -- Для project-service
        CREATE SCHEMA IF NOT EXISTS task_service_schema; -- Для task-service
        CREATE SCHEMA IF NOT EXISTS notification_service_schema; -- Для notification-service
        CREATE SCHEMA IF NOT EXISTS activity_service_schema; -- Для activity-service
        CREATE SCHEMA IF NOT EXISTS auth_service_schema; -- Для auth-service
        CREATE SCHEMA IF NOT EXISTS file_service_schema; -- Для file-service
        CREATE SCHEMA IF NOT EXISTS comment_service_schema; -- Для comment-service
        CREATE SCHEMA IF NOT EXISTS reporting_schema; -- Для reporting-service
        CREATE SCHEMA IF NOT EXISTS search_service_schema; -- Для search-service
    END
$$;