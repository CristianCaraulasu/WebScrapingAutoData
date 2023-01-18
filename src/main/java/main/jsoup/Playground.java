package main.jsoup;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Playground {

    public static void readDataAndMapBrands(String URL) {
        try {

//            //set HTTP proxy host to 147.56.0.11
//            System.setProperty("http.proxyHost", "147.56.0.11");
//
//            //set HTTP proxy port to 2071
//            System.setProperty("http.proxyPort", "2071");

            Document page = Jsoup.connect(URL).get();

            //First, we are sorting by brands (class="marki_blok")

            Elements mainElements = page.select(".marki_blok");

            Map<String, String> brandMapping = new HashMap<>();

            Map<String, String> finalMainMapping = brandMapping;
            mainElements.forEach(element -> {
                        finalMainMapping.put(element.text(), URL + element.attr("href").substring(4));
                    }
            );

            //the map is filtered in order to contain only brand car
            brandMapping = brandMapping.entrySet()
                    .stream()
                    .filter(pair -> !pair.getKey().equals("Toate mărcile"))
                    .collect(Collectors.toMap(pair -> pair.getKey(), key -> key.getValue()));

            Map<String, String> sortedMap = new TreeMap<>(brandMapping);

            sortedMap.forEach((key, value) -> {
                System.out.println(key + " " + value);
            });

            System.out.println("Our brandMapping has a size of " + sortedMap.size());

            readDataAndMapByModel(sortedMap, URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readDataAndMapByModel(Map<String, String> brandMapping, String URL) {

        Map<String, String> modelMapping = new TreeMap<>();

        //again we are sorting by specific selectors in order to obtain the cars
        brandMapping.forEach((key, value) -> {

            try {
                Document page = Jsoup.connect(value).get();
                Thread.sleep(100);


                Elements elements = page.select(".modeli");

                elements.forEach(e -> {

                    modelMapping.put(key + " - " + e.text(), URL + e.attr("href").substring(4));
                });


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        });

        modelMapping.forEach((key, value) -> {
            System.out.println(key + " " + value);
        });

        System.out.println("Our modelMapping map has a size of " + modelMapping.size());

        readDataAndMapByModelByGeneration(modelMapping, URL);

    }

    public static void readDataAndMapByModelByGeneration(Map<String, String> brandMapping, String URL) {

        Map<String, String> generationMapping = new TreeMap<>();

        //again we are sorting by specific selectors in order to obtain the cars
        brandMapping.forEach((key, value) -> {
            final Integer[] increase = {0};
            try {

                Document page = Jsoup.connect(value).get();
                Thread.sleep(100);


                Elements elements = page.select(".position");

                elements.forEach(e -> {
                    increase[0]++;

                    if (increase[0] % 2 == 1) {
                        generationMapping.put(key + " - " + e.text(), URL + e.attr("href").substring(4));
                        System.out.println(key + " - " + e.text() + " " + URL + e.attr("href").substring(4));
                    }

                });


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        });


        System.out.println("Our generationMapping map has a size of " + generationMapping.size());

        readDataAndMapByModelByEngine(generationMapping, URL);

    }

    public static void readDataAndMapByModelByEngine(Map<String, String> brandMapping, String URL) {

        Map<String, String> engineMapping = new TreeMap<>();

        //again we are sorting by specific selectors in order to obtain the cars
        brandMapping.forEach((key, value) -> {
            try {

                Document page = Jsoup.connect(value).get();
                Thread.sleep(100);


                Elements elements = page.select("th > a");

                elements.forEach(e -> {

                    engineMapping.put(key + " - " + e.getElementsByClass("tit").get(0).childNode(0), URL + e.attr("href").substring(4));
                    System.out.println(key + " - " + e.getElementsByClass("tit").get(0).childNode(0) + " " + URL + e.attr("href").substring(4));


                });


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        });


        System.out.println("Our engineMapping map has a size of " + engineMapping.size());
        writeDataIntoExcel(engineMapping);
    }

    public static void writeDataIntoExcel(Map<String, String> engineMap) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");
        AtomicInteger rownum = new AtomicInteger();
        Row row = sheet.createRow(rownum.getAndIncrement());
        Cell cell = row.createCell(0);
        cell.setCellValue("Producator -> Model -> Generatie -> Motorizare");
        cell = row.createCell(1);
        cell.setCellValue("Link");
        engineMap.forEach((key, value) -> {
            Row row1 = sheet.createRow(rownum.getAndIncrement());
            Cell cell1 = row1.createCell(0);
            cell1.setCellValue(key);
            cell1 = row1.createCell(1);
            cell1.setCellValue(value);
        });
        try {
            FileOutputStream out
                    = new FileOutputStream("C:\\Users\\Crixus\\Desktop\\test.xlsx");
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readDataFromExcelAndCreate(String fileName){
        try
        {
            FileInputStream file = new FileInputStream(new File(fileName));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            //I am not interested in the first row, which is only describing tabs
            rowIterator.next();

            // Create a workbook instances
            Workbook wb = new HSSFWorkbook();

            OutputStream os = new FileOutputStream("Geeks2.xlsx");

            // Creating a sheet using predefined class provided by Apache POI
            Sheet sheet2 = wb.createSheet("Final data excel for each car");

            Row row3 = sheet2.createRow(0);

            List<String> carSpecifications = List.of("Marcă","Model ","Generație ","Modificare (Tip motor) ","Anul inceperii productiei ","Anul opririi productiei ","Arhitectura grupului propulsor ","Tipul caroseriei ", "Număr de scaune ","Număr de uşi ","Consumul de combustibil - urban ","Consumul de combustibil - extra-urban ","Consumul de combustibil - mixt ","Tipul de combustibil ","Putere ","Cuplu ","Amplasarea motorului ","Volumul motorului ","Aspirația motorului ","Masă proprie ","Volumul minim al portbagajului ","Volumul maxim al portbagajului","Volumul rezervorului ","Lungime ","Lăţime ","Înălţime ","Numărul de viteze (cutie manuală) ","Numărul de viteze (cutie automată) ","Tractiune ","Dimensiunea pneurilor");
            Map<String,Integer> cellMap = new HashMap<>();
            final int[] i = {0};
            carSpecifications.forEach(element -> {
                cellMap.put(element,i[0]);
                Cell cell3 = row3.createCell(i[0]);
                cell3.setCellValue(element);
                i[0]++;
            });

            int index = 0;

            while (rowIterator.hasNext())
            {
                index++;
                if(index % 100 == 0)
                    System.out.println(index);

                //that't for writing in the other excel file
                Row row2 = sheet2.createRow(index);

                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                cellIterator.next(); // this is for the first cell in element, that is the car, not the URL
                Cell cell = cellIterator.next();

                String URL = cell.getStringCellValue();

                Document page = Jsoup.connect(URL).get();
                Thread.sleep(100);

                Elements elements = page.select(".cardetailsout");

                elements.forEach( element -> {

                    element.childNode(1).childNodes().forEach( child -> {

                        if(child.childNodes().size() > 3 && child.childNode(1).childNodes().size() > 0 && child.childNode(3).childNodes().size() > 0 && child.childNode(1).childNode(0) != null && carSpecifications.contains(child.childNode(1).childNode(0).toString()) && !child.childNode(1).childNode(0).toString().equals("Dimensiunea pneurilor")){
                             // Consum de combustibil - urban
                            Cell cell2 = row2.createCell(cellMap.get(child.childNode(1).childNode(0).toString()));
                            cell2.setCellValue(child.childNode(3).childNode(0).toString());
                        }
                        else if( child.childNodes().size() > 3 && child.childNode(1).childNodes().size() > 0 && child.childNode(1).childNode(0) != null && child.childNode(3).childNodes().size() > 1 && child.childNode(3).childNode(0).childNodes().size() > 0 && carSpecifications.contains(child.childNode(1).childNode(0).toString())){
                             //Dimensiunea pneurilor
                            Cell cell2 = row2.createCell(cellMap.get(child.childNode(1).childNode(0).toString()));
                            cell2.setCellValue(child.childNode(3).childNode(0).childNode(0).toString());
                        }
                        else if(child.childNodes().size() > 1 && child.childNode(0).childNodes().size() > 0 && child.childNode(0).childNode(0) != null && child.childNode(1).childNode(0).childNodes().size() > 0 && carSpecifications.contains(child.childNode(0).childNode(0).toString())){
                            //Marcă,Model,Generație
                            Cell cell2 = row2.createCell(cellMap.get(child.childNode(0).childNode(0).toString()));
                            cell2.setCellValue(child.childNode(1).childNode(0).childNode(0).toString());
                        }
                        else if(child.childNodes().size() > 3 && child.childNode(1).childNodes().size() > 0 && child.childNode(1).childNode(0) != null && child.childNode(3).childNodes().size() > 0 && carSpecifications.contains(child.childNode(1).childNode(0).toString())){
                            //Cuplu, Volumul motorului, Masă proprie, Volumul minim al portbagajului, Volumul maxim al portbagajului, Volumul rezervorului, Lungime, Lăţime, Înălţime
                            Cell cell2 = row2.createCell(cellMap.get(child.childNode(1).childNode(0).toString()));
                            cell2.setCellValue(child.childNode(3).childNode(0).toString());
                        }
                        else if(child.childNodes().size() > 1 && child.childNode(0).childNodes().size() > 0 && child.childNode(0).childNode(0) != null && child.childNode(1).childNodes().size() > 0  && carSpecifications.contains(child.childNode(0).childNode(0).toString())) {
                            //restul
                            Cell cell2 = row2.createCell(cellMap.get(child.childNode(0).childNode(0).toString()));
                            cell2.setCellValue(child.childNode(1).childNode(0).toString());
                        }
                    });

                });

            }
            wb.write(os);
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void modifyTheColumnsValueAndKeepOnlyTheDigitsWithin(int index){

        try
            {
                File file = new File("./DateMasinaDetaliu.xlsx");

                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                XSSFSheet sheet = wb.getSheetAt(0);
                Iterator<Row> itr = sheet.iterator();
                DataFormatter formatter = new DataFormatter();
                //itr.next();

                while (itr.hasNext())
                {
                    Row row = itr.next();

                    String value;

                    if(!(formatter.formatCellValue(row.getCell(index)).equals("")))
                        value = formatter.formatCellValue(row.getCell(index));
                    else
                    {
                        continue;
                    }

                    char[] year = value.toCharArray();
                    StringBuilder appender = new StringBuilder();


                    for(char c : year){
                       if(Character.isDigit(c))
                            appender.append(c);
                    }

                    if(!appender.toString().isEmpty())
                        row.getCell(index).setCellValue(Double.valueOf(appender.toString()));
                }

                FileOutputStream fileOut = new FileOutputStream("./DateMasinaDetaliu.xlsx");
                wb.write(fileOut);
                fileOut.close();

            }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
