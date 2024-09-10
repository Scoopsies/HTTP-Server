package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PingRouteHandlerTest {
    @Test
    void pingRouteRespondsImmediately() throws IOException {
        PingRouteHandler pingHandler = new PingRouteHandler();
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("GET /ping HTTP/1.1".getBytes()));

        byte[] result = pingHandler.handle(request);

        var time = pingHandler.getCurrentTime();
        var expected = """
                HTTP/1.1 200 OK\r
                Content-Type: text/html
                Server: httpServer\r
                \r
                <h2>Ping</h2>
                <li>start time: %s</li>
                <li>end time: %s</li>
                """.formatted(time, time);


        assertEquals(expected, new String(result));
    }

    @Test
    void ping1RouteResponds1SecondLater() throws IOException{
        PingRouteHandler pingHandler = new PingRouteHandler();
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("POST /ping/1 HTTP/1.1\r\n".getBytes()));

        String startTime = pingHandler.getCurrentTime();
        byte[] result = pingHandler.handle(request);
        String endTime = pingHandler.getCurrentTime();

        var expected = """
                HTTP/1.1 200 OK\r
                Content-Type: text/html
                Server: httpServer\r
                \r
                <h2>Ping</h2>
                <li>start time: %s</li>
                <li>end time: %s</li>
                """.formatted(startTime, endTime);

        assertEquals(expected, new String(result));
    }
}
