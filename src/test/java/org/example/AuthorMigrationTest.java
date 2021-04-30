package org.example;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class AuthorMigrationTest {
    @Test
    public void testAuthorMigration() {
        CSVParser csvParser = new CSVParser();
        List<List<String>> after = csvParser.loadFromFile("author_after.csv", ",");
        List<List<String>> expectations = csvParser.loadFromFile("author_expectation.csv", ",");

        assertTrue(CompareUtils.compare(after, expectations, "author_log.txt"));
    }
}
