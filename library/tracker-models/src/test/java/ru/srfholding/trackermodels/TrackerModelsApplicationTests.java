package ru.srfholding.trackermodels;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import ru.srfholding.trackermodels.config.TestContainersConfig;

@SpringBootTest
@ActiveProfiles("test")
class TrackerModelsApplicationTests {

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        TestContainersConfig.registerProperties(registry);
    }

    @Test
    void contextLoads() {
    }

}
