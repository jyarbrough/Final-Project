package services;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CsvReader {

    ArrayList<String> fetch() {

            File filePointer = new File("/Users/joeyarbrough/Advanced-Java-Labs/Homework/Final-Project/src/csvFiles/Categories.csv");
            Scanner input = null;

            try {
                input = new Scanner(filePointer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ArrayList<String> lines = new ArrayList<>();

            while(input.hasNext()) {
                String line = input.nextLine();
                lines.add(line);
            }
            input.close();
            return lines;
    }
}
