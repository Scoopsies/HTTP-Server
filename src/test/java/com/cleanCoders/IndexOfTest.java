package com.cleanCoders;

import com.cleanCoders.multipart.Body;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndexOfTest {
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
        assertEquals(0, IndexOf.xth(0, bodyBytes, boundary));
        assertEquals(101, IndexOf.xth(1, bodyBytes, boundary));
    }

    @Test
    void findsFirstOccurrenceOfBoundary() {
        assertEquals(0, IndexOf.first(bodyBytes, boundary));
    }

}
