package ru.holding.srf.ptmsmigrations;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
@DisplayName("Проверка соответствия сущностей и схемы БД")
public class EntitySchemaConsistencyTest extends AbstractMigrationsTest {
    private static final String ENTITY_MODULE_NAME = "ru.srfholding.trackermodels";
    private static final String[] ENTITY_PACKAGES = {
            "ru.srfholding.trackermodels.user_service.model",
            "ru.srfholding.trackermodels.task_service.model",
            "ru.srfholding.trackermodels.project_service.model",
    };

    @BeforeAll
    public static void setupMigrations() throws Exception {
        initLiquibase("db/changelog/master-all-services.yaml");
        runMigrations();
    }

    @Test
    @DisplayName("Все Entity должны иметь соответствующие таблицы в правильных схемах")
    public void shouldHaveTablesForAllEntities() throws Exception {
        Set<Class<?>> entityClasses = findAllEntitiesClasses();

        assertFalse(entityClasses.isEmpty(),
                "Должны быть найдены Entity классы в модуле " + ENTITY_MODULE_NAME);

        log.info("Найдено классов Entity: {}", entityClasses.size());
        for (Class<?> entityClass : entityClasses) {
            String schemaName = getSchemaName(entityClass);
            String tableName = getTableName(entityClass);

            log.info("Проверка: {} -> {}.{}", entityClass.getSimpleName(), schemaName, tableName);

            assertTrue(tableExists(schemaName, tableName),
                    format("Таблица %s.%s для Entity %s должна существовать в БД", schemaName, tableName, entityClass.getSimpleName()));
        }
    }

    @Test
    @DisplayName("Все поля Entity должны иметь соотвтетсвующие колонки")
    public void shouldHaveColumnsForAllEntityFields() throws Exception {
        Set<Class<?>> entityClasses = findAllEntitiesClasses();
        DatabaseMetaData metaData = connection.getMetaData();
        for (Class<?> entityClass : entityClasses) {
            String schemaName = getSchemaName(entityClass);
            String tableName = getTableName(entityClass);
            Set<String> dbColumns = new HashSet<>();
            ResultSet columns = metaData.getColumns(null, schemaName, tableName, null);
            while (columns.next()) {
                dbColumns.add(columns.getString("COLUMN_NAME").toLowerCase());
            }

            if (dbColumns.isEmpty()) {
                log.info("Таблица: {}.{} не найдена или не имеет колонок!",schemaName, tableName);
                continue;
            }

            for (Field field : getAllFields(entityClass)) {
                if (shouldCheckField(field)) {
                    if (field.isAnnotationPresent(EmbeddedId.class)) {
                        Class<?> embeddedIdClass = field.getType();
                        log.info("Найден составной первичный ключ: {}", embeddedIdClass.getSimpleName());
                        for (Field embeddedIdField : embeddedIdClass.getDeclaredFields()) {
                            if (shouldCheckField(embeddedIdField)) {
                                String columnName = getColumnName(embeddedIdField).toLowerCase();
                                assertTrue(dbColumns.contains(columnName),
                                        format("Колонка %s для поля %s из Составного первичного ключа Entity %s должна существовать в таблице %s.%s",
                                                columnName, embeddedIdField.getName(), entityClass.getSimpleName(), schemaName, tableName));

                            }
                        }

                        continue;
                    }

                    if (field.isAnnotationPresent(Embedded.class)) {
                        Class<?> embeddedClass = field.getType();
                        log.info("Найден составной первичный ключ: {}", embeddedClass.getSimpleName());
                        for (Field embeddedIdField : embeddedClass.getDeclaredFields()) {
                            if (shouldCheckEmbeddedField(embeddedIdField)) {
                                String columnName = getColumnName(embeddedIdField);
                                assertTrue(dbColumns.contains(columnName),
                                        format("Колонка %s для поля %s в Entity %s должна существовать в таблице %s.%s",
                                                columnName, embeddedIdField.getName(), entityClass.getSimpleName(), schemaName, tableName));
                            }
                        }

                        continue;
                    }
                    String columnName = getColumnName(field);
                    assertTrue(dbColumns.contains(columnName),
                            format("Колонка: %s для поля: %s в Entity: %s должна существовать в таблице %s.%s", columnName, field.getName(), entityClass.getSimpleName(), schemaName, tableName));
                }
            }
        }
    }

    private Set<Class<?>> findAllEntitiesClasses() {
        try {
            Set<Class<?>> classes = new HashSet<>();
            for (String packageName : ENTITY_PACKAGES) {
                Reflections reflections = new Reflections(packageName, Scanners.TypesAnnotated);
                classes.addAll(reflections.getTypesAnnotatedWith(Entity.class));
            }

            return classes;
        } catch (Exception e) {
            log.error("Не удалось найти Entity классы причина: {}", e.getMessage(), e);
            return Collections.emptySet();
        }
    }

    private String getSchemaName(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.schema().isEmpty()) {
            return tableAnnotation.schema();
        }

        return "public";
    }

    private String getTableName(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }

        return camelCaseToSnakeCase(entityClass.getSimpleName());
    }

    private String  getColumnName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        }

        JoinColumn joinColumnAnnotation = field.getAnnotation(JoinColumn.class);
        if (joinColumnAnnotation != null && !joinColumnAnnotation.name().isEmpty()) {
            return joinColumnAnnotation.name();
        }
        return camelCaseToSnakeCase(field.getName());
    }

    private boolean shouldCheckField(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return false;
        }

        if (Collections.class.isAssignableFrom(field.getType())) {
            return false;
        }

        if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
            return false;
        }

        return true;
    }
    private boolean shouldCheckEmbeddedField(Field embeddedIdField) {
        if (embeddedIdField.isAnnotationPresent(Transient.class)) {
            return false;
        }

        if (Modifier.isStatic(embeddedIdField.getModifiers())) {
            return false;
        }

        return true;
    }


    private Field findIdField(Class<?> entityClass) {

        for (Field field : entityClass.getFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }

        return null;
    }

    private boolean isFieldNullable(Field field) {
        Column columnAttribute = field.getAnnotation(Column.class);
        if (columnAttribute != null) {
            return columnAttribute.nullable();
        }

        JoinColumn joinColumnAnnotation = field.getAnnotation(JoinColumn.class);
        if (joinColumnAnnotation != null) {
            return joinColumnAnnotation.nullable();
        }

        return true;
    }

    private List<Field> getAllFields(Class<?> entityClass) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClazzEntity = entityClass;
        while (currentClazzEntity != null && currentClazzEntity != Object.class) {
            fields.addAll(Arrays.asList(currentClazzEntity.getDeclaredFields()));
            currentClazzEntity = currentClazzEntity.getSuperclass();
        }

        return fields;
    }

    private String camelCaseToSnakeCase(String str) {
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2".toLowerCase());
    }
}
