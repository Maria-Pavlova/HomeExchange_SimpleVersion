package com.example.homeexchange_simpleversion.utils;

import com.example.homeexchange_simpleversion.services.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Scheduler {
    private final OfferService offerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);


    public Scheduler(OfferService offerService) {
        this.offerService = offerService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredOffers(){
        offerService.deleteExpiredOffers();
        LOGGER.info("At " + LocalDate.now() + " checked and deleted expired offers");
    }
}
