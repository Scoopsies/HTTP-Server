package com.cleanCoders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class HttpRequest {
    HashMap<String, String> requestMap = new HashMap<>();

    public HttpRequest() {}

    public HttpRequest(InputStream inputStream) throws IOException {
        parseRequest(getRequest(inputStream));
    }

    public String getRequest(InputStream inputStream) {
        StringBuilder request = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            readHeader(reader, request);
            parseHeader(request.toString());
            request.append("\r\n");
            maybeReadBody(reader, request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return request.toString();
    }

    private static void readHeader(BufferedReader reader, StringBuilder request) throws IOException {
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            request.append(line).append("\r\n");
        }
    }

    private void maybeReadBody(BufferedReader reader, StringBuilder request) throws IOException {
        String contentLength = requestMap.get("Content-Length");

        if (contentLength != null) {
            int intLength = Integer.parseInt(contentLength);
            char[] body = new char[intLength];
            int actualRead = reader.read(body, 0, intLength);
            request.append(body, 0, actualRead);
            parseBody(body);
        }
    }

    private void parseBody(char[] body) {
        this.requestMap.put("body", new String(body));
        parseBoundary();
        parseContentType();
        parseBodyHeader();
        parseFileName();
    }

    private void parseFileName() {
        String contentDisposition = getContentDisposition();
        String fileName = contentDisposition.split(";")[1].split("=")[1].replace("\"", "");
        this.requestMap.put("file name", fileName);
    }

    private void parseBodyHeader() {
        String header = this.requestMap.get("body").split("\r\n\r\n")[0];
        this.requestMap.put("body header", header);
    }

    private void parseContentType() {
        String contentType = this.requestMap.get("body").split("\r\n")[2].replace("Content-Type: ", "");
        this.requestMap.put("content type", contentType);
    }

    private void parseBoundary() {
        String boundary = this.requestMap.get("Content-Type").replace("multipart/form-data; boundary=", "");
        this.requestMap.put("boundary", boundary);
    }

    private void parseHeader(String header) {
        requestMap.put("header", header);
        requestMap.put("startLine", parseStartLine(header));
        requestMap.put("path", parsePath(header));
        requestMap.put("method", parseMethod(header));
        addHeaderLines(requestMap.get("header"));
    }

    public void parseRequest(String request) {
        requestMap.put("request", request);
    }

    private void addHeaderLines(String request) {
        var headerLines = request.split("\r\n");
        for (int i = 1; i < headerLines.length; i++) {
            var headerLine = headerLines[i].split(": ", 2);
            if (Objects.equals(2, headerLine.length)) {
                this.requestMap.put(headerLine[0], headerLine[1]);
            }
        }
    }

    private String getContentDisposition() {
        String body = this.requestMap.get("body");
        if (body != null) {
            return body.split("\r\n")[1].replace("Content-Disposition: form-data;", "");
        }
        return "";
    }

    private String parsePath(String request) {
        return splitStartLine(request)[1];
    }

    private String parseMethod(String request) {
        return splitStartLine(request)[0];
    }

    private String parseStartLine(String request) {
        return request.split("\r\n")[0];
    }

    private String[] splitStartLine(String request) {
        return parseStartLine(request).split("\\s");
    }

    public String get(String parameter) {
        return requestMap.get(parameter);
    }
}