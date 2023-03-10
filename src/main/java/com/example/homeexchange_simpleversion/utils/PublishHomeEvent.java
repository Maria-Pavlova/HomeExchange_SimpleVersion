package com.example.homeexchange_simpleversion.utils;

import com.example.homeexchange_simpleversion.models.entities.Home;
import org.springframework.context.ApplicationEvent;

public class PublishHomeEvent extends ApplicationEvent {

    private Home home;

    public Home getHome() {
        return home;
    }

    public PublishHomeEvent setHome(Home home) {
        this.home = home;
        return this;
    }

    public PublishHomeEvent(Object source) {
        super(source);

    }
}
