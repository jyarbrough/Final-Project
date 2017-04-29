package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CsvReader {

    ArrayList<String> fetch(String pathName) {

        File filePointer = new File(pathName);
        Scanner input = null;

        ArrayList<String> lines = new ArrayList<>();

        try {
            input = new Scanner(filePointer);

            while (input.hasNext()) {
                String line = input.nextLine();
                lines.add(line);
            }

            input.close();
        } catch (FileNotFoundException e) {
//                e.printStackTrace();
        }
        return lines;
    }
}
