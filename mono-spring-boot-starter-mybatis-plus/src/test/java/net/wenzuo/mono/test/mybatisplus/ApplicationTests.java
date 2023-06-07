package net.wenzuo.mono.test.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.mybatisplus.config.MybatisPlusAutoConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Catch
 * @since 2023-06-06
 */
@Slf4j
@SpringBootTest
class ApplicationTests {

    @Resource
    private AnnotationConfigApplicationContext applicationContext;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(applicationContext.getBean(MybatisPlusAutoConfiguration.class));
        Assertions.assertNotNull(applicationContext.getBean(MybatisPlusInterceptor.class));
        Assertions.assertFalse(applicationContext.containsBean("fillMetaObjectHandler"));
    }

}
