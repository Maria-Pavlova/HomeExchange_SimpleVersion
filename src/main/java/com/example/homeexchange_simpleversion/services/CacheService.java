package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.utils.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class CacheService {

   private final CacheManager cacheManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class);

   @Autowired
    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void evictCaches(){
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName))
                        .clear());
    }

    @Scheduled(cron = "5 * * * * *")
    public void evictAllCachesAtIntervals() {
        evictCaches();
        LOGGER.info("At " + LocalDate.now() + " cache was evict");
    }
}
