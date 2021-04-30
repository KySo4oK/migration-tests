package org.example;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CSVParser {
    public List<List<String>> loadFromFile(String fileName, String regex) {
        try (FileReader reader = new FileReader(fileName)) {
            Scanner scan = new Scanner(reader);
            ArrayList<String> lines = new ArrayList<>();
            scan.nextLine();
            while (scan.hasNextLine()) {
                lines.add(scan.nextLine());
            }
            return lines.stream()
                    .map(line -> Arrays.asList(line.split(regex)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("failed in - " + fileName + " with  -" + e.getMessage());
        }
    }
}
