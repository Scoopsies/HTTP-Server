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
        }
    }

    private void parseHeader(String header) {
        requestMap.put("header", header);
        requestMap.put("startLine", parseStartLine(header));
        requestMap.put("path", parsePath(header));
        requestMap.put("method", parseMethod(header));
        addHeaderLines(requestMap.get("header"));
    }

    public void parseRequest(String request) {
        String header = requestMap.get("header") + "\r\n";
        requestMap.put("request", request);
        String body = requestMap.get("request").replace(header, "");
        requestMap.put("body", body);
    }

    private void addHeaderLines(String request) {
        var headerLines = request.split("\r\n");
        for (int i = 1; i < headerLines.length; i++) {
            var headerLine = headerLines[i].split(": ", 2);
            if (Objects.equals(2, headerLine.length)) {
                requestMap.put(headerLine[0], headerLine[1]);
            }
        }
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