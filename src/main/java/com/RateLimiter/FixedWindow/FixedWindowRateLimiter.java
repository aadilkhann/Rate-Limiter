package com.RateLimiter.FixedWindow;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter {
    private final long windowSizeInMillis;
    private final long maxRequests;

    private  final ConcurrentHashMap<String, Window> userWindow = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(long windowSizeInSecond, long maxRequests) {
        this.windowSizeInMillis = windowSizeInSecond*1000;
        this.maxRequests = maxRequests;
    }

    public boolean allowRequest(String userID){
        long currentTime = System.currentTimeMillis();

        userWindow.putIfAbsent(userID, new Window(currentTime, new AtomicInteger(0)));

        Window window=userWindow.get(userID);

        synchronized (window){
            if(currentTime - window.startTime >= windowSizeInMillis){
                //reset window
                window.startTime = currentTime;
                window.requestCount.set(0);
            }

            if(window.requestCount.get() < maxRequests){
                window.requestCount.incrementAndGet();
                System.out.println("Request allowed for user "+userID);
                return true;
            }
            else {
                System.out.println("Request denied for user "+userID);
                return false;
            }
        }

    }

    static class Window{
        long startTime;
        AtomicInteger requestCount;
        Window(long startTime, AtomicInteger requestCount){
            this.startTime = startTime;
            this.requestCount = requestCount;
        }
    }
}
