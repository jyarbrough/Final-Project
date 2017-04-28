package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CsvReader {

    ArrayList<String> fetch(String pathName) {

            File filePointer = new File(pathName);
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
