package com.example.db.model;

import lombok.NonNull;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class CarModel {

    @EmbeddedId
    private CarIdentification carIdentification;

    @NonNull
    private Integer productionYear;

    private Integer endYearProduction;

    @NonNull
    private Engine engineArhitecture;
    @NonNull
    private String carBody;

    private String numberOfSeats; //this could be a range

    private String numberOfDoors; //this also could a range

    private Double townConsumption;

    private Double outsideTownConsumption;

    private Double mixedConsumption;

    private String fuelType;

    private Integer horsePower;

    private Integer carCouple;

    private String enginePlacement;

    @NonNull
    private Integer engineVolume;

    private String engineAspiration;

    private Integer weight;

    private Integer trunkVolume;

    private Integer fuelVolume;

    private Integer length;

    private Integer width;

    private Integer height;

    private String numberOfManualGears;

    private String numberOfAutomatedGears;

    private TractionType tractionType;

    private String tyreDimension;

}
