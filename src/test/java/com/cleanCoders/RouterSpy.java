package com.cleanCoders;

public class RouterSpy extends Router {

    public RouteHandler getRoute(String route) {
        return this.routes.get(route);
    }

}
