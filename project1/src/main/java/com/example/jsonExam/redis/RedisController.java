package com.example.jsonExam.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/project1/redis")
public class RedisController {

    private final StringRedisTemplate redisTemplate;

    // 생성자 주입 (Lombok 없이)
    public RedisController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 저장
    @PostMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "Saved";
    }

    // 저장 + TTL
    @PostMapping("/set-expire")
    public String setWithExpire(@RequestParam String key, @RequestParam String value,
                                @RequestParam long seconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds));
        return "Saved with TTL";
    }

    // 조회
    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 삭제
    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        redisTemplate.delete(key);
        return "Deleted";
    }

    // TTL 확인
    @GetMapping("/ttl")
    public long getTTL(@RequestParam String key) {
        return redisTemplate.getExpire(key);
    }
}
