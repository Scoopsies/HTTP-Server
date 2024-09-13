package com.cleanCoders;

import com.cleanCoders.routes.*;

public class Main {
    public static void main(String[] args) {
        Router router = new Router();
        ArgParser parsedArgs = new ArgParser();

//        String[] testArgs = {"-r", "./testRoot"};
        parsedArgs.parseArgs(args);

        router.setDefaultRoot(parsedArgs.getRoot());
        router.addRoute("/hello", new HelloRouteHandler());
        router.addRoute("/ping", new PingRouteHandler());
        router.addRoute("/listing", new ListingRouteHandler(parsedArgs.getRoot()));
        router.addRoute("/form", new FormRouteHandler());
        router.addRoute("/guess", new GuessRouteHandler());

        Server server = new Server(router, parsedArgs.getPort());

        if (parsedArgs.getRunStatus()) {
            Printables.printStartupConfig(parsedArgs.getRoot(), parsedArgs.getPort());
            server.run();
        }
    }
}