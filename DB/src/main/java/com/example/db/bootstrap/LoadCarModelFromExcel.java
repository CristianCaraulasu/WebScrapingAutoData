package com.example.db.bootstrap;

import com.example.db.model.Car;
import com.example.db.model.Engine;
import com.example.db.model.TractionType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
@Profile("load-data")
public class LoadCarModelFromExcel implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        DataFormatter formatter = new DataFormatter();
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("DateMasinaDetaliu_used_for_JPA.xlsx");

        if (resource == null)
            throw new RuntimeException("Unable to access xlsx resource");

        File file = new File(resource.toURI());

        XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();

        //I'm skipping the first row
        rowIterator.next();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Car car = Car.builder()
                    .brand(row.getCell(0).getStringCellValue())
                    .model(row.getCell(1).getStringCellValue())
                    .generation(row.getCell(2).getStringCellValue())
                    .engineType(row.getCell(3).getStringCellValue())
                    .productionYear((int)row.getCell(4).getNumericCellValue())
                    .endYearProduction((int)row.getCell(5).getNumericCellValue())
                    .engineArhitecture(Engine.fromValue(row.getCell(6).getStringCellValue()))
                    .carBody(row.getCell(7).getStringCellValue())
                    .numberOfSeats(row.getCell(8).getStringCellValue())
                    .numberOfDoors(row.getCell(9).getStringCellValue())
                    .townConsumption(
                            row.getCell(10).getStringCellValue() != null ? Double.valueOf(row.getCell(10).getStringCellValue()) : null
                    )
                    .outsideTownConsumption(
                            row.getCell(11).getStringCellValue() != null ? Double.valueOf(row.getCell(11).getStringCellValue()) : null
                    )
                    .mixedConsumption(
                            row.getCell(12).getStringCellValue() != null ? Double.valueOf(row.getCell(12).getStringCellValue()) : null
                    )
                    .fuelType(row.getCell(13).getStringCellValue())
                    .horsePower((int)row.getCell(14).getNumericCellValue())
                    .carCouple((int)row.getCell(15).getNumericCellValue())
                    .enginePlacement(row.getCell(16).getStringCellValue())
                    .engineVolume((int)row.getCell(17).getNumericCellValue())
                    .engineAspiration(row.getCell(18).getStringCellValue())
                    .weight((int)row.getCell(19).getNumericCellValue())
                    .trunkVolume((int)row.getCell(20).getNumericCellValue())
                    .fuelVolume((int)row.getCell(21).getNumericCellValue())
                    .length((int)row.getCell(22).getNumericCellValue())
                    .width((int)row.getCell(23).getNumericCellValue())
                    .height((int)row.getCell(24).getNumericCellValue())
                    .numberOfManualGears(row.getCell(25).getStringCellValue())
                    .numberOfAutomatedGears(row.getCell(26).getStringCellValue())
                    .tractionType(TractionType.valueOf(row.getCell(27).getStringCellValue()))
                    .tyreDimension(row.getCell(28).getStringCellValue())
                    .build();

            System.out.println(car);

        }

    }

}


