package com.cleanCoders;

import com.cleanCoders.routes.*;

public class Main {
    public static void main(String[] args) {
        Router router = new Router();
        ArgParser parsedArgs = new ArgParser();

        String[] testArgs = {"-r", "./testRoot"};
        parsedArgs.parseArgs(testArgs);

        router.setDefaultRoot(parsedArgs.getRoot());
        router.addRoute("/hello", new HelloRouteHandler());
        router.addRoute("/ping", new PingRouteHandler());
        router.addRoute("/listing", new ListingRouteHandler(parsedArgs.getRoot()));
        router.addRoute("/form", new FormRouteHandler());

        Server server = new Server(router, parsedArgs.getPort(), parsedArgs.getRoot());

        if (parsedArgs.getRunStatus())
            server.run();
    }
}