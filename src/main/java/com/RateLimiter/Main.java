package com.RateLimiter;

import com.RateLimiter.FixedWindow.RedisFixedWindowRateLimiter;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        FixedWindowRateLimiter rateLimiter = new FixedWindowRateLimiter(10, 10);
//         for (int i = 0; i < 20; i++) {
//             if (i<10) rateLimiter.allowRequest("user");
//             else rateLimiter.allowRequest("user-1");
//         }


//        RedisFixedWindowRateLimiter rateLimiter = new RedisFixedWindowRateLimiter(10, 5);
//        for (int i = 0; i < 20; i++) {
//            boolean allowed = rateLimiter.allowRequest("user");
////            boolean allowed = limiter.allowRequest("user1");
//            System.out.println("Request " + i + " allowed: " + allowed);
//        }

//        SlidingWindowRateLimiter rateLimiter=new SlidingWindowRateLimiter(10,10);
//        for (int i=0;i<20;i++){
//            boolean allowed=rateLimiter.allowRequest("user");
//            System.out.println("Request " + i  + " allowed: " + allowed);

//            if(i==9)
                Thread.sleep(800);
//        RedisFixedWindowRateLimiter rateLimiter = new RedisFixedWindowRateLimiter(10, 5);
//        for (int i = 0; i < 20; i++) {
//            boolean allowed = rateLimiter.allowRequest("user");
////            boolean allowed = limiter.allowRequest("user1");
//            System.out.println("Request " + i + " allowed: " + allowed);
//        }

        SlidingWindowRateLimiterOptimised slidingWindowRateLimiterOptimised=new SlidingWindowRateLimiterOptimised(10,10,5);
//        slidingWindowRateLimiterOptimised.allowRequest("user");
        for (int i = 0; i < 20; i++) {
            boolean allowed = slidingWindowRateLimiterOptimised.allowRequest("user");
//            System.out.println("Request " + i + " allowed: " + allowed);
        }
    }
}