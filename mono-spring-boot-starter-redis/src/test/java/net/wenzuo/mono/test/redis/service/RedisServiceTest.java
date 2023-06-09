package net.wenzuo.mono.test.redis.service;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.redis.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Catch
 * @since 2023-06-15
 */
@Slf4j
@SpringBootTest
class RedisServiceTest {

    @Resource
    private RedisService redisService;

    @Test
    void set() {
        List<Item> items = List.of(new Item("test", 1), new Item("test", 2));
        redisService.set("items", items);
        List<Item> list = redisService.get("items", List.class, Item.class);
        list.forEach(item -> log.info("item:{}", item));

        Map<String, Item> itemMap = new HashMap<>();
        itemMap.put("item1", new Item("test", 1));
        itemMap.put("item2", new Item("test", 2));
        redisService.set("itemMap", itemMap);
        Map<String, Item> map = redisService.get("itemMap", Map.class, String.class, Item.class);
        map.forEach((key, value) -> log.info("key:{},value:{}", key, value));
    }

    @Test
    void testSet() {
    }

    @Test
    void get() {
    }

    @Test
    void testGet() {
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

    @AllArgsConstructor
    @Data
    private static class Item {

        private String name;
        private Integer age;

    }

}