package com.cleanCoders;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

public class HttpRequest {
    HashMap<String, String> headerMap = new HashMap<>();
    final byte[] doubleCRLF = "\r\n\r\n".getBytes();
    byte[] body;

    public HttpRequest() {}

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        byte[] headerBytes = readHeader(bis);
        parseHeader(headerBytes);
        maybeParseBody(bis);
    }

    private byte[] readHeader(BufferedInputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] checker = new byte[1];
        int matchIndex = 0;

        while (inputStream.read(checker) != -1) {
            baos.write(checker[0]);
            if (checker[0] == doubleCRLF[matchIndex]) {
                matchIndex++;
                if (matchIndex == doubleCRLF.length) {
                    break;
                }
            } else {
                matchIndex = 0;
            }
        }

        return baos.toByteArray();
    }

    private void maybeParseBody(BufferedInputStream inputStream) throws IOException {
        String contentLengthStr = get("Content-Length");
        if (contentLengthStr != null) {
            int contentLength = Integer.parseInt(contentLengthStr);
            this.body = new byte[contentLength];
            inputStream.readNBytes(this.body, 0, contentLength);
        }
    }

    private void parseHeader(byte[] headerBytes) {
        String header = new String(headerBytes);
        headerMap.put("header", header);
        headerMap.put("startLine", parseStartLine(header));
        headerMap.put("path", parsePath(header));
        headerMap.put("method", parseMethod(header));
        addHeaderLines(headerMap.get("header"));
        parseBoundary();
    }

    private void parseBoundary() {
        String contentType = this.headerMap.get("Content-Type");
        if ((contentType != null) && contentType.contains("multipart/form-data; boundary=")) {
            String boundary = contentType.replace("multipart/form-data; boundary=", "");
            boundary = "--" + boundary;
            this.headerMap.put("boundary", boundary);
        }
    }

    private void addHeaderLines(String request) {
        var headerLines = request.split("\r\n");
        for (int i = 1; i < headerLines.length; i++) {
            var headerLine = headerLines[i].split(": ", 2);
            if (Objects.equals(2, headerLine.length)) {
                this.headerMap.put(headerLine[0], headerLine[1]);
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
        return headerMap.get(parameter);
    }

    public byte[] getBody() {
        return this.body;
    }
}