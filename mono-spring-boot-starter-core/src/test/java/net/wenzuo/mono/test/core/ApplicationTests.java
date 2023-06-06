package net.wenzuo.mono.test.core;

import jakarta.annotation.Resource;
import net.wenzuo.mono.core.config.CoreAsyncConfiguration;
import net.wenzuo.mono.core.config.CoreAutoConfiguration;
import net.wenzuo.mono.core.config.CoreJsonConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * @author Catch
 * @since 2023-06-06
 */
@SpringBootTest
class ApplicationTests {

    @Resource
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(applicationContext.getBean(CoreAutoConfiguration.class));
        Assertions.assertNotNull(applicationContext.getBean(CoreAsyncConfiguration.class));
        Assertions.assertNotNull(applicationContext.getBean(CoreJsonConfiguration.class));
    }

}
