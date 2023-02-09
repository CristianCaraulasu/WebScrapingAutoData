package com.example.db.model;

import lombok.NonNull;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CarIdentification implements Serializable {

    @NonNull
    private String brand;
    @NonNull
    private String model;
    @NonNull
    private String generation;
    @NonNull
    private String engineType;

}
