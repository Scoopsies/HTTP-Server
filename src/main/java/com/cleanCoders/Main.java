package com.cleanCoders;

import com.cleanCoders.routes.*;

import java.io.IOException;

//public class RouterSpy extends Router {
//
//    public RouteHandler getRoute(String route) {
//        return this.routes.get(route);
//    }
//}

public class Main {

    private final Router router;

    public Main(Router router) {
        this.router = router;
    }

    public void start(String[] args) throws IOException {
        ArgParser parsedArgs = new ArgParser();
        parsedArgs.parseArgs(args);
        IServerSocket serverSocket = new ServerSocket(new java.net.ServerSocket(parsedArgs.getPort()));

        router.setDefaultRoot(parsedArgs.getRoot());
        router.addRoute("/hello", new HelloRouteHandler());
        router.addRoute("/ping", new PingRouteHandler());
        router.addRoute("/listing", new ListingRouteHandler(parsedArgs.getRoot()));
        router.addRoute("/form", new FormRouteHandler());
        router.addRoute("/guess", new GuessRouteHandler());

        Server server = new Server(router, serverSocket);

        if (parsedArgs.getRunStatus()) {
            Printables.printStartupConfig(parsedArgs.getRoot(), parsedArgs.getPort());
            server.run();
        }
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main(new Router());
        main.start(args);
    }
}