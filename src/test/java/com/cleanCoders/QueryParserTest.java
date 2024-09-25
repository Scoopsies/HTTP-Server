package com.cleanCoders;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryParserTest {

    @Test
    void parseParseOneQuery() {
        String path = "/hamburger?foo=1";
        HashMap<String, String> map = QueryParser.parse(path);

        assertEquals("1", map.get("foo"));
    }

    @Test
    void parseParsesMultipleQueries() {
        String path = "/hamburger?foo=1&bar=2";
        HashMap<String, String> map = QueryParser.parse(path);

        assertEquals("1", map.get("foo"));
        assertEquals("2", map.get("bar"));
    }

}
