package com.example.db.repository;

import com.example.db.model.Car;
import com.example.db.model.CarIdentification;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<Car,CarIdentification> {

    CarIdentification findByCarIdentification(CarIdentification carIdentification);

    @PostConstruct
     default void say(){
        System.out.println("YEEEAH MAMA");
    }
}


