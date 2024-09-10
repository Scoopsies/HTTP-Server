package com.cleanCoders;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

public class HttpRequest {
    HashMap<String, String> requestMap = new HashMap<>();

    public HttpRequest(InputStream inputStream) throws IOException {
        parseRequest(getRequest(inputStream));
    }

    public String getRequest(InputStream inputStream) throws IOException {
        StringBuilder request = new StringBuilder();
        byte[] buffer = new byte[16384];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            request.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
            if (bytesRead < buffer.length) {
                break;
            }
        }

        return request.toString();
    }

    public void parseRequest(String request) {
        requestMap.put("request", request);
        requestMap.put("header", parseHeader(request));
        requestMap.put("startLine", parseStartLine(request));
        requestMap.put("body", parseBody(request));
        requestMap.put("path", parsePath(request));
        requestMap.put("method", parseMethod(request));
        addHeaderLines(requestMap, request);
    }

    private void addHeaderLines(HashMap<String, String> result, String request) {
        var headerLines = request.split("\r\n");
        for (int i = 1; i < headerLines.length; i++) {
            var headerLine = headerLines[i].split(": ", 2);
            if (Objects.equals(2, headerLine.length)) {
                result.put(headerLine[0], headerLine[1]);
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

    private String parseHeader(String request) {
        return request.split("\r\n\r\n", 2)[0];
    }

    private String parseBody(String request) {
        try {
            return request.split("\r\n\r\n", 2)[1];
        } catch (Exception e) {
            return null;
        }
    }

    public String get(String parameter) {
        return requestMap.get(parameter);
    }
}