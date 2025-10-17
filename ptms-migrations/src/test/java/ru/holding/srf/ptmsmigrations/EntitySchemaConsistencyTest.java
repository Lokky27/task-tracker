package ru.holding.srf.ptmsmigrations;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Field;
import java.util.*;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Проверка соответствия сущностей и схемы БД")
public class EntitySchemaConsistencyTest extends AbstractMigrationsTest {
    private static final String ENTITY_PACKAGE = "ru.srfholding.trackermodels";

    @BeforeAll
    public static void setupMigrations() throws Exception {
        initLiquibase("db/changelog/master-all-services.yaml");
        runMigrations();
    }

    @Test
    @DisplayName("Все Entity должны иметь соответствующие таблицы в правильных схемах")
    public void shouldHaveTablesForAllEntities() throws Exception {
        Set<Class<?>> entityClasses = findAllEntitiesClasses();

        assertFalse(entityClasses.isEmpty(), "Должны быть найдены Entity классы в пакете: " + ENTITY_PACKAGE);

        System.out.printf("\n=== Найдено классов Entity: %d ===", entityClasses.size());
        for (Class<?> entityClass : entityClasses) {
            String schemaName = getSchemaName(entityClass);
            String tableName = getTableName(entityClass);

            System.out.printf("Проверка: %s -> %s.%s", entityClass.getSimpleName(), schemaName, tableName);

            assertTrue(tableExists(schemaName, tableName),
                    format("Таблица %s.%s для Entity %s должна существовать в БД", schemaName, tableName, entityClass.getSimpleName()));
        }
    }

    private Set<Class<?>> findAllEntitiesClasses() {
        try {
            Reflections reflections = new Reflections(ENTITY_PACKAGE, Scanners.TypesAnnotated);
            return reflections.getTypesAnnotatedWith(Entity.class);
        } catch (Exception e) {
            System.err.println("Не удалось найти Entity классы причина: " + e.getMessage());
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
