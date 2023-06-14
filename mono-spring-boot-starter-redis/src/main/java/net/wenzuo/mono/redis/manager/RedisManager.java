package net.wenzuo.mono.redis.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.core.utils.JsonUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author Catch
 * @since 2023-06-14
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RedisManager {

    private final StringRedisTemplate stringRedisTemplate;

    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, JsonUtils.toString(value), timeout, timeUnit);
    }

    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JsonUtils.toString(value));
    }

    public <T> T get(String key, Class<T> clazz) {
        return JsonUtils.toBean(stringRedisTemplate.opsForValue().get(key), clazz);
    }

    public <T> T get(String key, Class<?> wrapper, Class<?>... inners) {
        return JsonUtils.toBean(stringRedisTemplate.opsForValue().get(key), wrapper, inners);
    }

    public Boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    public Long del(Collection<String> keys) {
        return stringRedisTemplate.delete(keys);
    }

    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return stringRedisTemplate.expire(key, timeout, timeUnit);
    }

    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public Long incr(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    public Long decr(String key, long delta) {
        return stringRedisTemplate.opsForValue().decrement(key, delta);
    }

}
