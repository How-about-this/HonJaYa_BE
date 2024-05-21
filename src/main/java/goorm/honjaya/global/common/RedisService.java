package goorm.honjaya.global.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    // TTL 설정 X
    public void set(String key, Object value) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, value);
    }

    // refreshToken TTL 설정 O
    public void set(String key, Object value, Duration duration) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, value, duration);
    }

    // redis에 저장된 refreshToken 삭제
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // redis에 저장된 refreshToken 조회
    public Object get(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        if (ops.get(key) == null) {
            return null;
        }
        return ops.get(key);
    }

    public boolean exists(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        return ops.get(key) != null;
    }
}

