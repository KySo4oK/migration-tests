package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static final String CONNECTION_FAILURE = "Connection failure.";

    public static List<String> columns = List.of("id", "name", "age", "genre");

    public static void main(String[] args) throws FileNotFoundException {
        writeCSV(readData("select * from author"), "author_before.csv");
        writeCSV(readData("select * from book"), "book_before.csv");

        migrate("alter table author add column age int default 21;");
        migrate("alter table book add column genre varchar default 'drama';");

        writeCSV(readData("select * from author"), "author_after.csv");
        writeCSV(readData("select * from book"), "book_after.csv");
    }

    private static void migrate(String query) {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println(CONNECTION_FAILURE);
            e.printStackTrace();
        }
    }

    public static void writeCSV(List<List<String>> data, String CSV_FILE_NAME) throws FileNotFoundException {
        List<String> dataLines = data.stream()
                .map(d -> String.join(",", d))
                .collect(Collectors.toList());

        File csvOutputFile = new File(CSV_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.forEach(pw::println);
        }
    }

    private static Connection getConnection() throws java.sql.SQLException {
        return DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres",
                        "postgres", "postgres");
    }

    public static List<List<String>> readData(String query) {
        List<List<String>> data = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                data.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(CONNECTION_FAILURE);
            e.printStackTrace();
        }
        return data;
    }

    private static List<String> parseResultSet(ResultSet resultSet) {
        List<String> result = new LinkedList<>();
        for (String column : columns) {
            String value = null;
            try {
                value = resultSet.getString(column);
            } catch (SQLException ignored) {

            }
            if (value != null) {
                result.add(value);
            }
        }
        return result;
    }
}
