package ru.holding.srf.ptmsmigrations;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Testcontainers
public abstract class AbstractMigrationsTest {
    private static final String POSTGRES_IMAGE = "postgres:16-alpine";
    protected static final String USER_DETAILS_SERVICE_SCHEMA = "user_details_service_schema";
    protected static final String PROJECT_SERVICE_SCHEMA = "project_service_schema";
    protected static final String TASK_SERVICE_SCHEMA = "task_service_schema";
    protected static final Set<String> ALL_SCHEMAS = Set.of(
            USER_DETAILS_SERVICE_SCHEMA,
            PROJECT_SERVICE_SCHEMA,
            TASK_SERVICE_SCHEMA
    );

    @Container
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE))
            .withDatabaseName("testDb")
            .withUsername("testUser")
            .withPassword("test")
            .withReuse(true)
            .withLabel("reuse-key", "migration-tests")
            .withStartupTimeout(Duration.ofMinutes(5));
    protected static Connection connection;
    protected static Database database;
    protected static Liquibase liquibase;

    @BeforeAll
    public static void setUpContainer() throws SQLException, DatabaseException {
        postgres.start();
        connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        database.setAutoCommit(false);
    }

    @AfterAll
    public static void tearDownContainer() {
        liquibase = null;
        database = null;
        connection = null;
     }

     public static void cleanupContainers() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "rm", "-f",
                    "${docker ps -a -q --filter label=reuse-key=migration-tests}"
            );
            processBuilder.start().waitFor();
            System.out.println("Контейнеры очищены!");
        } catch (Exception e) {
            System.err.println("Неудалось очистить контейнеры: " + e.getMessage());
        }
     }

    /**
     * Инициализация Liquibase с указанным changelog файлом
     */
    protected static void initLiquibase(String changelogPath) throws Exception {
        if (liquibase != null) {
            try {
                liquibase.close();
            } catch (Exception e) {
                //Ignoring
            }
        }

        liquibase = new Liquibase(changelogPath, new ClassLoaderResourceAccessor(), database);
    }

    /**
     * Запуск миграций
     */
    protected static void runMigrations() throws Exception {
        if (liquibase == null) {
            throw new IllegalStateException("Liquibase неинициализирован. Требуется вызов initLiquibase()");
        }

        liquibase.update("");
    }

    /**
     * Получение рабочего соендинения с БД (создает новое, если текущее закрыто)
     */
    private static Connection getWorkingConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    postgres.getJdbcUrl(),
                    postgres.getUsername(),
                    postgres.getPassword()
            );
        }

        return connection;
    }

    /**
     * Получение соединения для использования в тестах
     */
    protected static Connection getConnection() throws Exception {
        return getWorkingConnection();
    }

    /**
     * Пересоздание соединения с БД (если нужно)
     */
    protected static void reconnect() throws Exception {
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
            } catch (Exception e) {
                //Игнорируем
            }
        }

        connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        database.setAutoCommit(false);

        if (liquibase != null) {
            String changelogPath = liquibase.getDatabaseChangeLog().getFilePath();
            liquibase = new Liquibase(changelogPath, new ClassLoaderResourceAccessor(), database);
        }
    }

    /**
     * Проверка существования схемы
     */
    protected boolean schemaExists(String databaseSchema) throws Exception {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(" +
                    "SELECT 1 FROM information_schema.schemata WHERE schema_name = '" + databaseSchema +"')");
            return resultSet.next() && resultSet.getBoolean(1);
        }
    }

    /**
     * Проверка существования указанной таблицы в схеме
     */
    protected boolean tableExists(String schemaName, String tableName) throws Exception {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(" +
                    "SELECT 1 FROM information_schema.tables " +
                    "WHERE table_schema = '" + schemaName + "'" +
                    "AND table_name = '" + tableName + "'" +
                    ")"
            );

            return resultSet.next() && resultSet.getBoolean(1);
        }
    }

    /**
     * Получение списка всех таблиц в указанной схем
     */
    protected Set<String> getTablesFromSchema(String schemaName) throws Exception {
        Set<String> tableNames = new HashSet<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT table_name FROM information_schema.tables " +
                    "WHERE table_schema = '" + schemaName + "' " +
                    "AND table_type = 'BASE_TABLE'");

            while (resultSet.next()) {
                tableNames.add(resultSet.getString("table_name"));
            }
        }

        return tableNames;
    }

    /**
     * Проверка существования колонки в таблице
     */
    protected boolean columnExists(String schemaName, String tableName, String columnName) throws Exception {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(" +
                    "SELECT 1 FROM information_schema.columns " +
                    "WHERE table_schema = '" + schemaName + "' " +
                    "AND table_name = '" + tableName + "' " +
                    "AND column_name = '" + columnName + "')"
            );

            return resultSet.next() && resultSet.getBoolean(1);
        }
    }

    /**
     * Проверка наличие первичного ключа на таблице
     */
    protected boolean hasPrimaryKey(String schemaName, String tableName) throws Exception {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(" +
                    "SELECT 1 FROM information_schema.table_constraints " +
                    "WHERE table_schema = '" + schemaName + "' " +
                    "AND table_name = '" + tableName + "' " +
                    "AND constraint_type = 'PRIMARY KEY')"
            );

            return resultSet.next() && resultSet.getBoolean(1);
        }
    }

    /**
     * Проверка наличия внешенего ключа
     */
    protected boolean hasForeignKey(String schemaName, String tableName, String columnName) throws Exception {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT EXISTS(" +
                    "SELECT 1 FROM information_schema.key_column_usage kcu " +
                    "JOIN information_schema.table_constraints tc " +
                    "ON kcu.constraint_name = tc.constraint_name " +
                    "AND kcu.table_name = tc.table_name " +
                    "WHERE kcu.table_schema = '" + schemaName + "' " +
                    "AND kcu.table_name = '" + tableName + "' " +
                    "AND kcu.column_name = '" + columnName + "' " +
                    "AND tc.constraint_name = 'FOREIGN KEY')"
            );

            return resultSet.next() && resultSet.getBoolean(1);
        }
    }

    /**
     * Получение количества выполненных миграций для конкретного сервиса
     */
    protected int getMigrationsCountForService(String serviceName) throws Exception {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT count(*) FROM public.databasechangelog " +
                            "WHERE filename LIKE '%" + serviceName + "%'"
            );

            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }

    /**
     * Получение количества выполненных миграций
     */
    protected int getExecutedMigrationsCount() throws Exception {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT count(*) FROM public.databasechangelog"
            );

            return resultSet.next() ? resultSet.getInt(1) : 0;
        }
    }

    /**
     * Получение JDBC URL для подключения
     */
    protected static String getJdbcUrl() {
        return postgres.getJdbcUrl();
    }

    /**
     * Получить username для подключения
     */
    protected static String getUsername() {
        return postgres.getUsername();
    }

    /**
     * Получить password для подключения
     */
    protected static String getPassword() {
        return postgres.getPassword();
    }
}
