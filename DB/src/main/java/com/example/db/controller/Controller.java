package com.example.db.controller;

import com.example.db.model.CarIdentification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    @GetMapping(value="/data",produces = MediaType.APPLICATION_JSON_VALUE)
    public CarIdentification getData(){
        return CarIdentification.builder()
                .model("m")
                .generation("n")
                .brand("b")
                .engineType("t")
                .build();
    }


}
