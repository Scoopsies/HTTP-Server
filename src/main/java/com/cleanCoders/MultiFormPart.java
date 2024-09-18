package com.cleanCoders;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MultiFormPart {
    String name;
    String fileName;
    byte[] content;

    public MultiFormPart(byte[] body, byte[] boundary) {
        String stringBody = new String(body);
        String contentDisposition = stringBody.lines()
                .filter(line -> line.contains("Content-Disposition:"))
                .collect(Collectors.joining());

        this.name = parseContentDisposition(contentDisposition, "name");

        if (contentDisposition.contains("filename="))
            this.fileName = parseContentDisposition(contentDisposition, "filename");

        this.content = parseContent(body, boundary);
    }

    private byte[] slice(byte[] src, int startIdx, int endIdx) {
        byte[] result = new byte[src.length - endIdx];
        System.arraycopy(src, startIdx, result, 0, endIdx);

        return result;
    }

    private byte[] parseContent(byte[] body, byte[] boundary) {
        final byte[] CRLF = "\r\n\r\n".getBytes();
        int headerEndIndex = indexOfFirst(body, CRLF) + CRLF.length;
        byte[] endOfHeaderToEnd = slice(body, headerEndIndex, body.length - headerEndIndex);
        int contentEndIndex = indexOfFirst(endOfHeaderToEnd, boundary) - 1;

        return slice(endOfHeaderToEnd, 0, contentEndIndex);
    }

    private static String parseContentDisposition(String contentDisposition, String key) {
        return Arrays.stream(contentDisposition
                        .split(";"))
                        .filter(section -> section.matches("(^|\\s+)%s.*".formatted(key)))
                        .collect(Collectors.joining())
                        .split("=")[1]
                        .replace("\"", "");
    }

    public String getName() {
        return this.name;
    }

    public String getFileName() {
        return this.fileName;
    }

    public byte[] parseContent() {
        return "content".getBytes();
    }

    public int indexOfFirst(byte[] content, byte[] doubleCRLF) {
        for (int i = 0; i < content.length; i++) {

            if (content[i] == doubleCRLF[0]) {
                int counter = 0;
                for (int j = 0; j < doubleCRLF.length; j++) {
                    if (doubleCRLF[j] != content[i + j])
                        break;

                    counter++;
                }
                if (counter == doubleCRLF.length)
                    return i;
            }
        }
        return -1;
    }
}
