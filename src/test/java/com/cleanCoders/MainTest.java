package com.cleanCoders;

import com.cleanCoders.routes.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void routeHandlerGetsDefaultPaths() throws IOException {
        RouterSpy routerSpy = new RouterSpy();
        Main main = new Main(routerSpy);
        String[] args = {"-x"};
        main.start(args);

        assertInstanceOf(HelloRouteHandler.class, routerSpy.getRoute("/hello"));
        assertInstanceOf(PingRouteHandler.class, routerSpy.getRoute("/ping"));
        assertInstanceOf(ListingRouteHandler.class, routerSpy.getRoute("/listing"));
        assertInstanceOf(FormRouteHandler.class, routerSpy.getRoute("/form"));
        assertInstanceOf(GuessRouteHandler.class, routerSpy.getRoute("/guess"));
    }

}
