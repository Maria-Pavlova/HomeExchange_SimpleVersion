package com.example.homeexchange_simpleversion.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{
    @Column
    private String url;
    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;


    public String getUrl() {
        return url;
    }

    public Picture setUrl(String url) {
        this.url = url;
        return this;
    }

    public Home getHome() {
        return home;
    }

    public Picture setHome(Home home) {
        this.home = home;
        return this;
    }
}