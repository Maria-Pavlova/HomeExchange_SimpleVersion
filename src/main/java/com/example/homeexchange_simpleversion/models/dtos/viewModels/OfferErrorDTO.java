package com.example.homeexchange_simpleversion.models.dtos.viewModels;

public class OfferErrorDTO {
    private final Long id;
    private final String description;

    public OfferErrorDTO(Long id, String description) {
        this.id = id;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
