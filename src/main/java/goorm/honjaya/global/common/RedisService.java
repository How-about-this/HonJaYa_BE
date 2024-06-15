package goorm.honjaya.global.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    // TTL 설정 X
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // refreshToken TTL 설정 O
    public void set(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    // redis에 저장된 refreshToken 삭제
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // redis에 저장된 refreshToken 조회
    public Object get(String key) {
        if (redisTemplate.opsForValue().get(key) == null) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    public boolean exists(String key) {
        return redisTemplate.opsForValue().get(key) != null;
    }
}

