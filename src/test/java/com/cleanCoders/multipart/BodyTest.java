package com.cleanCoders.multipart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BodyTest {
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
    void parsesSinglePartBody() {
        Body body = new Body(bodyBytes, boundary);
        assertArrayEquals("<h1>hello</h1>".getBytes(), body.getPart("hello").getContent());
    }

    @Test
    void parsesDoublePartBody() {
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

        byte[] boundary = "--BOUNDARY".getBytes();

        Body body = new Body(bodyBytes, boundary);

        assertArrayEquals("<h1>hello</h1>".getBytes(), body.getPart("hello").getContent());
        assertArrayEquals("<h1>goodbye</h1>".getBytes(), body.getPart("goodbye").getContent());
    }
}