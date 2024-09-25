package com.cleanCoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileContentTest {
    FileContent fileContent;

    @BeforeEach
    void setup() {
        fileContent = new FileContent();
    }

    @Test
    void getFileContentGetsHtmlFile() throws IOException {
        String content = fileContent.getTextFileContent("/Users/scoops/Projects/HttpServer/testRoot/hello/index.html");
        String expected = "<h1>Hello!</h1>\n";
        assertEquals(expected, content);
    }

    @Test
    void getExtensionOfReturnsCorrectExtension() {
        assertEquals("txt", fileContent.getExtensionOf("file.txt"), "The extension should be 'txt'");
        assertEquals("html", fileContent.getExtensionOf("index.html"), "The extension should be 'html'");
        assertEquals("jpg", fileContent.getExtensionOf("image.jpg"), "The extension should be 'jpg'");
    }

    @Test
    void isTextFileReturnsTrueForTextExtensions() {
        assertTrue(fileContent.isTextFile("txt"), "Files with .txt extension should be recognized as text files");
        assertTrue(fileContent.isTextFile("html"), "Files with .html extension should be recognized as text files");
        assertFalse(fileContent.isTextFile("jpg"), "Files with .jpg extension should not be recognized as text files");
        assertFalse(fileContent.isTextFile("pdf"), "Files with .pdf extension should not be recognized as text files");
    }

    @Test
    public void testGetResourceTextFileContent_ValidResource() throws IOException {
        String result = fileContent.getResourceTextFileContent("404/index.html");
        assertNotNull(result);
        assertEquals("<h1>404: This isn't the directory you are looking for.</h1>\n", result);
    }

    @Test
    void getContentTypeReturnsHTML() {
        String contentType = fileContent.getContentType("/hello/welcome.html");
        assertEquals("Content-Type: text/html", contentType);
    }

    @Test
    void getContentTypeReturnsPNG() {
        String contentType = fileContent.getContentType("/things/miata.png");
        assertEquals("Content-Type: image/png", contentType);
    }

    @Test
    void getContentTypeReturnsGIF() {
        String contentType = fileContent.getContentType("/things/miata.gif");
        assertEquals("Content-Type: image/gif", contentType);
    }

    @Test
    void getContentTypeReturnsJPG() {
        String contentType = fileContent.getContentType("/things/miata.jpg");
        assertEquals("Content-Type: image/jpeg", contentType);
    }

    @Test
    void getContentTypeReturnsPDF() {
        String contentType = fileContent.getContentType("/things/miata.pdf");
        assertEquals("Content-Type: application/pdf", contentType);
    }
}
