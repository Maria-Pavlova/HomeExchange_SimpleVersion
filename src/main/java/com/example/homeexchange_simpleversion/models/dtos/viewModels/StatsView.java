package com.example.homeexchange_simpleversion.models.dtos.viewModels;

import lombok.Data;


public class StatsView {
    private final int authRequests;
    private final int anonymousRequests;


    public StatsView(int authRequests, int anonymousRequests) {
        this.authRequests = authRequests;
        this.anonymousRequests = anonymousRequests;
    }

    public int getTotalRequests(){
        return authRequests + anonymousRequests;
    }

    public int getAuthRequests() {
        return authRequests;
    }

    public int getAnonymousRequests() {
        return anonymousRequests;
    }
}
