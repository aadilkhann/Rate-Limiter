package com.RateLimiter.SlidingWindow;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter {
    private final long windowSizeInMillis;
    private final int windowSize;

    private Deque<Long> userQueue = new ArrayDeque<>();

    private  final ConcurrentHashMap<String, Deque<Long>> userWindow = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(long windowSizeInMillis,int windowSize) {
        this.windowSizeInMillis = windowSizeInMillis;
        this.windowSize = windowSize;
    }

    public boolean tryAcquire(String userID) {
        long now = System.currentTimeMillis();

        userWindow.computeIfAbsent(userID, k -> new ArrayDeque<>()).addLast(now);

        Deque<Long> queue = userWindow.get(userID);

        return true;
    }
}
