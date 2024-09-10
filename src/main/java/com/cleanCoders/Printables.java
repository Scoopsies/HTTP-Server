package com.cleanCoders;

import java.io.File;
import java.io.IOException;

public class Printables {

    public static void printStartupConfig(String root, int port) {
        var file = new File(root);
        try {
            String path = file.getCanonicalPath();
            System.out.println("Example Server");
            System.out.println("Running on port: " + port);
            System.out.println("Serving files from: " + path);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void printHelpMenu() {
        System.out.println("  -p     Specify the port.  Default is 80.");
        System.out.println("  -r     Specify the root directory.  Default is the current working directory.");
        System.out.println("  -h     Print this help message");
        System.out.println("  -x     Print the startup configuration without starting the server");
    }
}
