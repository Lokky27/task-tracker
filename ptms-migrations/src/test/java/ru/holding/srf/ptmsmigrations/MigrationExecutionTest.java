package ru.holding.srf.ptmsmigrations;

import liquibase.Contexts;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("Тесты выполнения миграций")
public class MigrationExecutionTest extends AbstractMigrationsTest {

    @BeforeAll
    public static void setUp() throws Exception {
        initLiquibase("db/changelog/master-all-services.yaml");
    }

    @Test
    @DisplayName("Проверка что миграции накатываются без ошибок")
    public void shouldApplyAllMigrationsSuccessfully() {
        assertDoesNotThrow(AbstractMigrationsTest::runMigrations, "Миграции накатываются без исключений");
    }

    @Test
    @DisplayName("Таблица DATABASECHANGELOG создается в public схеме")
    public void shouldCreateDatabaseChangelogTable() throws Exception {
        runMigrations();
        assertTrue(tableExists("public", "databasechangelog"),
                "Таблица databasechangelog должна соществовать в схеме public");
    }

    @Test
    @DisplayName("Таблица DATABASECHANGELOGLOCK создается в public схеме")
    public void shouldCreateDatabaseChangelogLockTable() throws Exception {
        reconnect();

        assertTrue(tableExists("public", "databasechangeloglock"),
                "Таблица databasechangeloglock должна сушествовать в схеме public");
    }

    @Test
    @DisplayName("Должны быть выполнены миграции для всех сервисов")
    public void shouldExecuteMigrationsForAllServices() throws Exception {
        runMigrations();

        int totalMigrations = getExecutedMigrationsCount();
        int userDetailsServiceTotalMigrations = getMigrationsCountForService("user_service");
        int projectServiceTotalMigrations = getMigrationsCountForService("project_service");
        int taskServiceTotalMigrations = getMigrationsCountForService("task_service");

        assertAll(() -> {
            assertThat(totalMigrations)
                    .isGreaterThan(0)
                    .isEqualTo(24);
            assertThat(userDetailsServiceTotalMigrations)
                    .isGreaterThan(0)
                    .isEqualTo(7);
            assertThat(projectServiceTotalMigrations)
                    .isGreaterThan(0)
                    .isEqualTo(7);
            assertThat(taskServiceTotalMigrations)
                    .isGreaterThan(0)
                    .isEqualTo(10);
        });
    }

    @Test
    @DisplayName("Повторное выполнение миграций должно быть идемпотентным")
    public void shouldBeIdempotent() throws Exception {
        runMigrations();
        int firstRunCount = getExecutedMigrationsCount();

        assertDoesNotThrow(AbstractMigrationsTest::runMigrations,
                "Повторное выполнение миграций должно быть идемпотентным");

        int secondRunCount = getExecutedMigrationsCount();

        assertEquals(firstRunCount, secondRunCount,
                "Кол-во миграций не должно измениться при повторном выполнении");
    }

    @Test
    @DisplayName("Все схемы микросервисов должны быть созданы")
    public void shouldCreateAllServiceSchemas() throws Exception {
        runMigrations();

        for (String schemaName : ALL_SCHEMAS) {
            assertTrue(schemaExists(schemaName),
                    "Схема: " + schemaName + " должна быт создана");
        }
    }

    @Test
    @DisplayName("Миграции не должны создавать дублирующиеся changeset'ы")
    public void shouldNotHaveDuplicateChangeSets() throws Exception {
        runMigrations();
        try(Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, author, filename, count(*) as count " +
                            "FROM public.databasechangelog " +
                            "GROUP BY id, author, filename " +
                            "HAVING count(*) > 1;"
            );

            assertFalse(resultSet.next(),
                    "Не должно быть дублирующихся записей в databasechangelog");
        } catch (Exception e) {
            log.error("Ошибка подключения к БД", e);
        }
    }

    @Test
    @DisplayName("Rollback последней миграции должен работать корректно")
    public void shouldRollbackSuccessfully() throws Exception {
        runMigrations();

        int initialCount = getExecutedMigrationsCount();
        if (initialCount > 0) {
            assertDoesNotThrow(() -> liquibase.rollback(1, String.valueOf(new Contexts())),
                    "Rollback должен выполнится без ошибок");

            int afterRollbackCount = getExecutedMigrationsCount();
            assertTrue(afterRollbackCount < initialCount,
                    "После rollback должно быть меньше миграций");
        }
    }
}
