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
                this.port = (Integer.parseInt(args[i + 1]));

            if (Objects.equals(args[i], "-r"))
                this.root = (args[i + 1]);

            if (Objects.equals(args[i], "-h")) {
                this.isRunnable = false;
                Printables.printHelpMenu();
            }

            if (Objects.equals(args[i], "-x")) {
                this.isRunnable = false;
                isPrintingConfig = true;
            }
        }

        if (isPrintingConfig)
            Printables.printStartupConfig(root, port);
    }

    public String getRoot() {
        return this.root;
    }

    public int getPort() {
        return this.port;
    }

    public boolean getRunStatus() {
        return this.isRunnable;
    }
}