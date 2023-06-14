package net.wenzuo.mono.test.redis.manager;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.redis.manager.RedisManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Catch
 * @since 2023-06-14
 */
@Slf4j
@SpringBootTest
class RedisManagerTest {

    @Resource
    private RedisManager redisManager;

    @Test
    void set() {
        redisManager.set("test", "test");
        String test = redisManager.get("test", String.class);
        log.info("test: {}", test);
    }

    @Test
    void testSet() {
    }

    @Test
    void get() {
    }

    @Test
    void getList() {
    }

    @Test
    void getSet() {
    }

    @Test
    void del() {
    }

    @Test
    void testDel() {
    }

    @Test
    void expire() {
    }

    @Test
    void getExpire() {
    }

    @Test
    void hasKey() {
    }

    @Test
    void incr() {
    }

    @Test
    void decr() {
    }

}