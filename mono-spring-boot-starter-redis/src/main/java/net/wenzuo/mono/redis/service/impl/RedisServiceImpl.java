package net.wenzuo.mono.redis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wenzuo.mono.core.utils.JsonUtils;
import net.wenzuo.mono.redis.service.RedisService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;

/**
 * @author Catch
 * @since 2023-06-15
 */
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "mono.redis.redis-service", matchIfMissing = true)
@Service
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, Object value, Duration duration) {
        stringRedisTemplate.opsForValue().set(key, JsonUtils.toJson(value), duration);
    }

    @Override
    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JsonUtils.toJson(value));
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return JsonUtils.toObject(stringRedisTemplate.opsForValue().get(key), clazz);
    }

    @Override
    public <T> T get(String key, Class<?> wrapper, Class<?>... inners) {
        return JsonUtils.toObject(stringRedisTemplate.opsForValue().get(key), wrapper, inners);
    }

    @Override
    public Boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public Long del(Collection<String> keys) {
        return stringRedisTemplate.delete(keys);
    }

    @Override
    public Boolean expire(String key, Duration duration) {
        return stringRedisTemplate.expire(key, duration);
    }

    @Override
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    @Override
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public Long incr(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decr(String key, long delta) {
        return stringRedisTemplate.opsForValue().decrement(key, delta);
    }

}
