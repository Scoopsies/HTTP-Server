package com.cleanCoders;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectoryBuilderTest {
    @Test
    void buildDirectoryListingForHello() throws IOException {
        DirectoryBuilder directoryBuilder = new DirectoryBuilder();
        File testDirectory = new File("./testRoot/hello");
        String result = directoryBuilder.build(testDirectory, ".");

        String expected ="<h1>Directory Listing for ./testRoot/hello</h1>\n"
        + "<ul>"
        + "<li><a href=\"/testRoot/hello/index.html\">index.html</a></li>"
        + "<li><a href=\"/testRoot/hello/miata.gif\">miata.gif</a></li>"
        + "</ul>";

        assertEquals(expected, result);
    }

    @Test
    void buildDirectoryListingForThings() throws IOException {
        DirectoryBuilder directoryBuilder = new DirectoryBuilder();
        File testDirectory = new File("./testRoot/things");
        String result = directoryBuilder.build(testDirectory, ".");

        String expected ="<h1>Directory Listing for ./testRoot/things</h1>\n"
                + "<ul>"
                + "<li><a href=\"/testRoot/things/miata.pdf\">miata.pdf</a></li>"
                + "<li><a href=\"/testRoot/things/miata.jpg\">miata.jpg</a></li>"
                + "<li><a href=\"/testRoot/things/miata.png\">miata.png</a></li>"
                + "<li><a href=\"/testRoot/things/miata.txt\">miata.txt</a></li>"
                + "<li><a href=\"/testRoot/things/miata.gif\">miata.gif</a></li>"
                + "</ul>";

        assertEquals(expected, result);
    }

    @Test
    void buildDirectoryListingForThingsWithString() throws IOException {
        DirectoryBuilder directoryBuilder = new DirectoryBuilder();
        String testDirectory = "./testRoot/things";
        String result = directoryBuilder.build(testDirectory, ".");

        String expected ="<h1>Directory Listing for ./testRoot/things</h1>\n"
                + "<ul>"
                + "<li><a href=\"/testRoot/things/miata.pdf\">miata.pdf</a></li>"
                + "<li><a href=\"/testRoot/things/miata.jpg\">miata.jpg</a></li>"
                + "<li><a href=\"/testRoot/things/miata.png\">miata.png</a></li>"
                + "<li><a href=\"/testRoot/things/miata.txt\">miata.txt</a></li>"
                + "<li><a href=\"/testRoot/things/miata.gif\">miata.gif</a></li>"
                + "</ul>";

        assertEquals(expected, result);
    }
}
