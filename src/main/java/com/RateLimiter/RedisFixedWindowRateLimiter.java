package com.RateLimiter;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisFixedWindowRateLimiter {

    private final int maxRequests;
    private final long windowSizeInSeconds;
    private final JedisPool jedisPool;

    public RedisFixedWindowRateLimiter(int maxRequests, long windowSizeInSeconds) {
        this.maxRequests = maxRequests;
        this.windowSizeInSeconds = windowSizeInSeconds;

        this.jedisPool = new JedisPool(
                new redis.clients.jedis.JedisPoolConfig(),
                "redis-19159.crce206.ap-south-1-1.ec2.cloud.redislabs.com",
                19159,
                2000,
                "default",    // username
                "M94fjGkEu3wCq9gTWpM9LJBUjzJ9ZZXo",    // password
                false
        );
    }

    public boolean allowRequest(String userId) {

        long currentTime = System.currentTimeMillis() / 1000;
        long windowNumber = currentTime / windowSizeInSeconds;

        String key = "rate_limit:" + userId + ":" + windowNumber;

        try (Jedis jedis = jedisPool.getResource()) {

            Long count = jedis.incr(key);

            if (count == 1) {
                jedis.expire(key, windowSizeInSeconds);
            }

            return count <= maxRequests;
        }
    }
}
