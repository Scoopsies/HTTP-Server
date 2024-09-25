package com.cleanCoders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {


    @Test
    void getPathRootExtractsCorrectPath() {
        Router router = new Router();
        assertEquals("/ping", router.getPathRoot("/ping/1") );
        assertEquals("/hello", router.getPathRoot("/hello/world"));
        assertEquals("/", router.getPathRoot("/"));
        assertEquals("/form", router.getPathRoot("/form?foo=1&bar=2"));
    }
}