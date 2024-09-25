package com.cleanCoders;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintablesTest {
    PrintStream stdOut;
    ByteArrayOutputStream baos;

    @BeforeEach
    void setup() {
        stdOut = System.out;
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
    }

    @AfterEach
    void cleanUp() {
        System.setOut(stdOut);
    }

    @Test
    void printHelpMenuPrintsTheDefaultConfigMenu() {
        String result = """
                Example Server
                Running on port: 80
                Serving files from: /Users/scoops/Projects/HttpServer
                """;
        Printables.printStartupConfig(".", 80);
        assertEquals(result, baos.toString());
    }

    @Test
    void printHelpMenuPrintsCustomSettingMenu() {
        String result = """
                Example Server
                Running on port: 8080
                Serving files from: /Users/scoops/Projects/HttpServer/src
                """;
        Printables.printStartupConfig("./src", 8080);
        assertEquals(result, baos.toString());
    }

    @Test
    void printHelpMenuPrintsTheHelpMenu() {
        String result = """
                  -p     Specify the port.  Default is 80.
                  -r     Specify the root directory.  Default is the current working directory.
                  -h     Print this help message
                  -x     Print the startup configuration without starting the server
                """;
        Printables.printHelpMenu();
        assertEquals(result, baos.toString());
    }
}