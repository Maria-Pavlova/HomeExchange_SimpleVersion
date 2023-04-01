package com.example.homeexchange_simpleversion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CacheService {

    @Autowired
    CacheManager cacheManager;

    public void evictCaches(){
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName))
                        .clear());
    }

    @Scheduled(cron = "0 0 * * * *")
    public void evictAllCachesAtIntervals() {
        evictCaches();
    }
}
