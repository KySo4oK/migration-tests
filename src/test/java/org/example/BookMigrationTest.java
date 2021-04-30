package org.example;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class BookMigrationTest {
    @Test
    public void testBookMigration() {
        CSVParser csvParser = new CSVParser();
        List<List<String>> after = csvParser.loadFromFile("book_after.csv", ",");
        List<List<String>> expectations = csvParser.loadFromFile("book_expectation.csv", ",");

        assertTrue(CompareUtils.compare(after, expectations, "book_log.txt"));
    }
}
