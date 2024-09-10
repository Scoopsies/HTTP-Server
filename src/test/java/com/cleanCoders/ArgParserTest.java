package com.cleanCoders;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ArgParserTest {
    ByteArrayOutputStream baos;
    PrintStream stdOut;
    ArgParser argParser;

    @BeforeEach
    void setup() {
        stdOut = System.out;
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        argParser = new ArgParser();
    }

    @AfterEach
    void cleanUp() {
        System.setOut(stdOut);
    }

    @Test
    void parseArgsSetsPort8080() {
        String[] args = {"-p", "8080"};
        ArgParser argParser = new ArgParser();
        argParser.parseArgs(args);
        assertEquals(8080, argParser.getPort());
    }

    @Test
    void parseArgsSetsPort800() {
        String[] args = {"-p", "800"};
        argParser.parseArgs(args);
        assertEquals(800, argParser.getPort());
    }

    @Test
    void parseArgsSetsRootToHello() {
        String[] args = {"-r", "./hello"};
        argParser.parseArgs(args);
        assertEquals("./hello", argParser.getRoot());
    }

    @Test
    void parseArgsSetsRootToGoodbye() {
        String[] args = {"-r", "./goodbye"};
        argParser.parseArgs(args);
        assertEquals("./goodbye", argParser.getRoot());
    }

    @Test
    void parseArgsSetsRootToHelloAndPortTo8080() {
        String[] args = {"-r", "./hello", "-p", "8080"};
        argParser.parseArgs(args);
        assertEquals("./hello", argParser.getRoot());
        assertEquals(8080, argParser.getPort());
    }

    @Test
    void parseArgsXPrintsSystemSettings() {
        String[] args = {"-x"};
        argParser.parseArgs(args);
        var result = """
                Example Server
                Running on port: 80
                Serving files from: /Users/scoops/Projects/HttpServer
                """;
        assertEquals(result, baos.toString());
    }

    @Test
    void parseArgsXDoesNotRunServer() {
        String[] args = {"-x"};
        argParser.parseArgs(args);
        assertFalse(argParser.getRunStatus());
    }

    @Test
    void parseArgsHDoesNotRunServer() {
        String[] args = {"-h"};
        argParser.parseArgs(args);
        assertFalse(argParser.getRunStatus());
    }

    @Test
    void parseArgsHPrintsHelpMenu() {
        String[] args = {"-h"};
        argParser.parseArgs(args);
        var result = """
                  -p     Specify the port.  Default is 80.
                  -r     Specify the root directory.  Default is the current working directory.
                  -h     Print this help message
                  -x     Print the startup configuration without starting the server
                """;
        assertEquals(result, baos.toString());
    }
}
