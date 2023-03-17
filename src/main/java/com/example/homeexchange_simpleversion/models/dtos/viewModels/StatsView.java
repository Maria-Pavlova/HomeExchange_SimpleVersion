package com.example.homeexchange_simpleversion.models.dtos.viewModels;

import lombok.Data;

@Data
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
}
