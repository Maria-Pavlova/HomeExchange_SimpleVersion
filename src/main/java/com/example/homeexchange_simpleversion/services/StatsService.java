package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.viewModels.StatsView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private int anonymousRequests, authRequests;
    public void onRequest(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails){
            authRequests++;
        }else {
            anonymousRequests++;
        }
    }

    public StatsView getStats(){
        return new StatsView(authRequests, anonymousRequests);
    }
}
