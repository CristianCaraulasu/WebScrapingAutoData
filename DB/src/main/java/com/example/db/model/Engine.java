package com.example.db.model;

import lombok.Getter;

@Getter
public enum Engine {

    INTERNAL_COMBUSTION("Motor cu combustie interna "),
    BEV_battery_electric_vehicle("BEV (Vehicul electric) "),
    FCEV_full_cell_electric_vehicle("FCEV (Vehicul electric cu pile de combustie) "),
    FHEV_full_hybrid_electric_vehicle("FHEV (vehicul electric complet hibrid) "),
    MHEV_mild_full_electric_vehicle("MHEV (Vehicul electric parțial hibrid) "),
    PFCEV_plug_in_fuel_cell_electric_vehicle("PFCEV (Vehicul electric cu pile de combustie plug-in) "),
    PHEV_plug_in_hybrid_electric_vehicle("PHEV (Vehicul electric hibrid Plug-in) ");

    private final String value;

    Engine(String value){
        this.value = value;
    }

    public static Engine fromValue(String value) {
        for (Engine e : values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }

}
