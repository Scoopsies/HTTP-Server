package com.cleanCoders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayUtilsTest {
    byte[] boundary = "--BOUNDARY".getBytes();
    byte[] bodyBytes = """
            --BOUNDARY\r
            Content-Disposition: form-data; name="hello"\r
            Content-Type: text/html\r
            \r
            <h1>hello</h1>\r
            --BOUNDARY--\r
            """.getBytes();


    @Test
    void findsXthOccurrenceOfBoundary() {
        assertEquals(0, ArrayUtils.xth(0, bodyBytes, boundary));
        assertEquals(101, ArrayUtils.xth(1, bodyBytes, boundary));
    }

    @Test
    void findsFirstOccurrenceOfBoundary() {
        assertEquals(0, ArrayUtils.first(bodyBytes, boundary));
    }

    @Test
    void countsBoundariesInSinglePart() {
        assertEquals(2, ArrayUtils.countOccurrencesOf(boundary, bodyBytes));
    }

    @Test
    void countsBoundariesInDoublePart() {
        byte[] bodyBytes = """
            --BOUNDARY\r
            Content-Disposition: form-data; name="hello"\r
            Content-Type: text/html\r
            \r
            <h1>hello</h1>\r
            --BOUNDARY\r
            Content-Disposition: form-data; name="goodbye"\r
            Content-Type: text/html\r
            \r
            <h1>goodbye</h1>\r
            --BOUNDARY--\r
            """.getBytes();

        assertEquals(3, ArrayUtils.countOccurrencesOf(boundary, bodyBytes));
    }

}
