package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CompareUtils {
    public static boolean compare(List<List<String>> after, List<List<String>> expectations, String fileName) {
        int differences = 0;

        List<String> lines = new ArrayList<>();

        int size = after.size();

        lines.add("after migration - " + size + " lines");
        lines.add("expected after migration - " + expectations.size() + " lines");
        lines.add("============================");

        for (int i = 0; i < size; i++) {
            List<String> afterLine = after.get(i);
            List<String> expectationLine = expectations.get(i);
            if (!afterLine.equals(expectationLine)) {
                lines.add("expected:");
                lines.add(String.join(",", afterLine));
                lines.add("but found: ");
                lines.add(String.join(",", expectationLine));
                lines.add("============================");
                differences++;
            }
        }

        lines.add("found - " + differences + " differences");

        try {
            Files.write(Path.of(fileName),lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return differences == 0;
    }
}
