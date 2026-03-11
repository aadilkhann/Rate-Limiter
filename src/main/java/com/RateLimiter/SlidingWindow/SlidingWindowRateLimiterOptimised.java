package com.RateLimiter.SlidingWindow;
import java.util.concurrent.*;

public class SlidingWindowRateLimiterOptimised {
    private final int maxRequests;
    private final long windowSizeInMillis;
    private final int bucketCount;
    private final long bucketSize;

    private final ConcurrentHashMap<String, UserBucket> userBuckets = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiterOptimised(int maxRequests, int windowSizeSeconds, int bucketCount) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeSeconds * 1000;
        this.bucketCount = bucketCount;
        this.bucketSize = windowSizeInMillis / bucketCount;
    }

    public boolean allowRequest(String userId) {

        long now = System.currentTimeMillis();

        userBuckets.putIfAbsent(userId, new UserBucket(bucketCount));

        UserBucket bucket = userBuckets.get(userId);

        synchronized (bucket) {

            int bucketIndex = (int)(now / bucketSize) % bucketCount;
            System.out.println(bucketIndex + " = " + now + " / " + bucketSize + " % " + bucketCount );

            if (bucket.timestamps[bucketIndex] != now / bucketSize) {
                bucket.timestamps[bucketIndex] = now / bucketSize;
                bucket.counts[bucketIndex] = 0;
            }

            int total = 0;

            for (int count : bucket.counts) {
                total += count;
            }

            if (total >= maxRequests) {
                return false;
            }

            bucket.counts[(int) bucketIndex]++;

            return true;
        }
    }

    static class UserBucket {

        int[] counts;
        long[] timestamps;

        UserBucket(int bucketCount) {
            counts = new int[bucketCount];
            timestamps = new long[bucketCount];
        }
    }
}

