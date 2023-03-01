package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.repositories.HomeRepository;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    private final HomeRepository homeRepository;

    public ExchangeService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }


}
