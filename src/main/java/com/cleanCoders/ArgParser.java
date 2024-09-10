package com.cleanCoders;

import java.util.Objects;

public class ArgParser {
    private String root = ".";
    private int port = 80;
    private boolean isRunnable = true;

    public void parseArgs(String[] args) {
        boolean isPrintingConfig = false;

        for (int i = 0; i < args.length; i++) {
            if (Objects.equals(args[i], "-p"))
                port = (Integer.parseInt(args[i + 1]));

            if (Objects.equals(args[i], "-r"))
                root = (args[i + 1]);

            if (Objects.equals(args[i], "-h")) {
                isRunnable = false;
                Printables.printHelpMenu();
            }

            if (Objects.equals(args[i], "-x")) {
                isRunnable = false;
                isPrintingConfig = true;
            }
        }

        if (isPrintingConfig)
            Printables.printStartupConfig(root, port);
    }

    public String getRoot() {
        return root;
    }

    public int getPort() {
        return port;
    }

    public boolean getRunStatus() {
        return isRunnable;
    }
}