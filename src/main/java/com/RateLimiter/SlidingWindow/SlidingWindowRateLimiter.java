package com.RateLimiter.SlidingWindow;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SlidingWindowRateLimiter {
    private final long windowSizeInMillis;
    private final int maxRequest;

    private  final ConcurrentHashMap<String, Deque<Long>> userWindow = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(long windowSizeInMillis,int maxRequest) {
        this.windowSizeInMillis = windowSizeInMillis * 1000;
        this.maxRequest = maxRequest;
    }

    public boolean allowRequest(String userID) {
        long now = System.currentTimeMillis();

        userWindow.putIfAbsent(userID, new ConcurrentLinkedDeque<>());

        Deque<Long> timeStamp = userWindow.get(userID);

        synchronized (timeStamp) {
            //Remove expired timestamp
            while (!timeStamp.isEmpty() && now - timeStamp.peekFirst() >= windowSizeInMillis) {
                timeStamp.pollFirst();
            }
            //Check Limit
            if (timeStamp.size() < maxRequest) {
                timeStamp.add(now);
                return true;
            } else
                return false;
        }
    }
}
