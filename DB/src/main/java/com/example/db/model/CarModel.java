package com.example.db.model;

import lombok.NonNull;

public class CarModel {

    @NonNull
    private String brand;
    @NonNull
    private String model;
    @NonNull
    private String generation;
    @NonNull
    private String engineType;
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



}
