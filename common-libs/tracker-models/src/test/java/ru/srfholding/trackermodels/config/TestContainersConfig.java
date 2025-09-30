package ru.srfholding.trackermodels.config;

//@Testcontainers
public class TestContainersConfig {
//    @Container
//    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16-alpine")
//            .withDatabaseName("task_tracker_test")
//            .withUsername("postgres")
//            .withPassword("postgres")
//            .withInitScript("sql/init_schema.sql")
//            .withReuse(true);
//
//    static {
//        container.start();
//    }
//
//    @DynamicPropertySource
//    public static void registerProperties(DynamicPropertyRegistry propertyRegistry) {
//        propertyRegistry.add("spring.datasource.url", container::getJdbcUrl);
//        propertyRegistry.add("spring.datasource.username", container::getUsername);
//        propertyRegistry.add("spring.datasource.password", container::getPassword);
//        propertyRegistry.add("spring.datasource.hikary.schema", () -> "task_tracker");
//        propertyRegistry.add("spring.jpa.properties.hibernate.default_schema", () -> "task_tracker");
//    }
}
