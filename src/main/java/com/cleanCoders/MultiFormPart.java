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

    private byte[] parseContent(byte[] body, byte[] boundary) {
        final byte[] doubleCrlf = "\r\n\r\n".getBytes();
        int contentStartIndex = indexOfFirst(body, doubleCrlf) + doubleCrlf.length;
        
        byte[] startOfContentToEnd = slice(body, contentStartIndex, body.length);
        System.out.println("startOfContentToEnd: " + new String(startOfContentToEnd));

        int contentEndIndex = indexOfFirst(startOfContentToEnd, boundary) - 2;

        return slice(startOfContentToEnd, 0, contentEndIndex);
    }

    private byte[] slice(byte[] src, int startIdx, int endIdx) {
        byte[] result = new byte[endIdx - startIdx];
        System.arraycopy(src, startIdx, result, 0, result.length);

        return result;
    }

    private static String parseContentDisposition(String contentDisposition, String key) {
        return Arrays.stream(contentDisposition
                        .split(";"))
                        .filter(section -> section.matches("(^|\\s+)%s.*".formatted(key)))
                        .collect(Collectors.joining())
                        .split("=")[1]
                        .replace("\"", "");
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

    public String getName() {
        return this.name;
    }

    public String getFileName() {
        return this.fileName;
    }

    public byte[] getContent() {
        return this.content;
    }
}
